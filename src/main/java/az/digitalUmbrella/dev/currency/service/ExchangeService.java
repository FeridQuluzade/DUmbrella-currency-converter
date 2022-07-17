package az.digitalUmbrella.dev.currency.service;

import az.digitalUmbrella.dev.currency.dto.CurrencyDetailDto;
import az.digitalUmbrella.dev.currency.dto.ExchangeDto;
import az.digitalUmbrella.dev.currency.dto.ExchangeSearchFilter;
import az.digitalUmbrella.dev.currency.dto.response.CurrencyResponse;
import az.digitalUmbrella.dev.currency.dto.response.ExchangeResponse;
import az.digitalUmbrella.dev.currency.error.ServiceException;
import az.digitalUmbrella.dev.currency.mapper.ExchangeMapper;
import az.digitalUmbrella.dev.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static az.digitalUmbrella.dev.currency.error.ErrorCodes.UNSUPPORTED_CURRENCY;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final CurrencyService currencyService;
    private final ExchangeMapper exchangeMapper;

    public ExchangeResponse getAllByDate(LocalDate date) {
        return ExchangeResponse.of(findAllByDate(date));
    }

    public ExchangeResponse getAllByCode(String to) {
        return ExchangeResponse.of(exchangeMapper.toExchanges(currencyService.getAllByCode(to)));
    }

    public ExchangeDto getByDateAndCode(ExchangeSearchFilter filter) {
        List<ExchangeDto> exchanges = findAllByDate(filter.getDate());
        return exchanges.stream()
                .filter(exchangeDto -> filter.getTo().equals(exchangeDto.getCode()))
                .findFirst()
                .orElseThrow(
                        () -> ServiceException.of(
                                UNSUPPORTED_CURRENCY,
                                "not support this currency: " + filter.getTo()));
    }

    private List<ExchangeDto> findAllByDate(LocalDate date) {
        CurrencyResponse allByDate = currencyService.getAllByDate(date);
        List<CurrencyDetailDto> currencyDetails = allByDate.getCurrencyDetails();
        return exchangeMapper.toExchangesFromCurrencyDetail(currencyDetails);
    }

}
