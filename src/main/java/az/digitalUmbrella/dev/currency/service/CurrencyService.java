package az.digitalUmbrella.dev.currency.service;

import az.digitalUmbrella.dev.currency.client.CBARClient;
import az.digitalUmbrella.dev.currency.dto.CurrencyDto;
import az.digitalUmbrella.dev.currency.dto.response.CurrencyResponse;
import az.digitalUmbrella.dev.currency.error.ServiceException;
import az.digitalUmbrella.dev.currency.mapper.CurrencyMapper;
import az.digitalUmbrella.dev.currency.model.Currency;
import az.digitalUmbrella.dev.currency.model.CurrencyCurs;
import az.digitalUmbrella.dev.currency.repository.CurrencyCursRepository;
import az.digitalUmbrella.dev.currency.repository.CurrencyRepository;
import az.digitalUmbrella.dev.currency.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static az.digitalUmbrella.dev.currency.error.ErrorCodes.CURRENCY_CURS_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CBARClient client;
    private final CurrencyCursRepository currencyCursRepository;
    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyResponse getAllByDate(LocalDate date) {
        LocalDate validDate = DateUtil.exceptWeekend(date);

        CurrencyCurs currencyCurs = getAllByDateFromDB(validDate)
                .orElseGet(() -> currencyCursRepository.save(getAllByDateFromClient(validDate)));

        return currencyMapper.toCurrencyResponse(currencyCurs, date);
    }

    protected List<CurrencyDto> getAllByCode(String code) {
        List<Currency> allByCode = currencyRepository.findAllByCode(code);
        return currencyMapper.toCurrencies(allByCode);
    }

    public void deleteAllByDate(LocalDate date) {
        var currencyCurs = getAllByDateFromDB(date).orElseThrow(
                () -> ServiceException.of(
                        CURRENCY_CURS_NOT_FOUND, "Currency curs not found by date: " + date));

        currencyCursRepository.delete(currencyCurs);
    }

    private Optional<CurrencyCurs> getAllByDateFromDB(LocalDate date) {
        log.info("Currencies fetched from database, date: {}", date);
        return currencyCursRepository.findCurrencyCursByRefreshDate(date);
    }

    private CurrencyCurs getAllByDateFromClient(LocalDate date) {
        log.info("Currencies fetched from client, date: {}", date);
        CurrencyCurs curs = currencyMapper.toCurrencyCurs(client.getRatesByDate(date));

        if (curs.isVacation(date)) {
            getAllByDateFromDB(curs.getRefreshDate())
                    .ifPresent(currencyCurs -> curs.setId(currencyCurs.getId()));
        }

        return curs;
    }

}
