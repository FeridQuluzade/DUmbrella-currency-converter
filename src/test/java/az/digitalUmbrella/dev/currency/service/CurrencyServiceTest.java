package az.digitalUmbrella.dev.currency.service;

import az.digitalUmbrella.dev.currency.client.CBARClient;
import az.digitalUmbrella.dev.currency.client.model.ValCurs;
import az.digitalUmbrella.dev.currency.dto.CurrencyDto;
import az.digitalUmbrella.dev.currency.dto.response.CurrencyResponse;
import az.digitalUmbrella.dev.currency.error.ServiceException;
import az.digitalUmbrella.dev.currency.mapper.CurrencyMapper;
import az.digitalUmbrella.dev.currency.model.Currency;
import az.digitalUmbrella.dev.currency.model.CurrencyCurs;
import az.digitalUmbrella.dev.currency.repository.CurrencyCursRepository;
import az.digitalUmbrella.dev.currency.repository.CurrencyRepository;
import az.digitalUmbrella.dev.currency.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static az.digitalUmbrella.dev.currency.error.ErrorCodes.CURRENCY_CURS_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    private static final LocalDate VALID_DATE =
            DateUtil.exceptWeekend(LocalDate.of(2022, 7, 25));

    @Mock
    private CBARClient client;

    @Mock
    private CurrencyCursRepository currencyCursRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyMapper currencyMapper;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void getAllByDate_WhenCurrencyCursExistInDB_ShouldReturnCurrencyCursFromDB() {
        //given
        var currencyCurs = new CurrencyCurs();
        currencyCurs.setRefreshDate(VALID_DATE);

        var expected = new CurrencyResponse();
        expected.setRequestDate(VALID_DATE);
        expected.setRefreshDate(VALID_DATE);

        //when
        when(currencyCursRepository.findCurrencyCursByRefreshDate(VALID_DATE))
                .thenReturn(Optional.of(currencyCurs));
        when(currencyMapper.toCurrencyResponse(currencyCurs, VALID_DATE)).thenReturn(expected);

        //then
        var actual = currencyService.getAllByDate(VALID_DATE);

        assertEquals(expected, actual);
        verify(currencyCursRepository, times(1)).findCurrencyCursByRefreshDate(VALID_DATE);
        verify(currencyMapper, times(1)).toCurrencyResponse(currencyCurs, VALID_DATE);
    }

    @Test
    void getAllByDate_WhenCurrencyCursNotExistInDB_ShouldReturnCurrencyFromClientAndSaveDB() {
        //given
        var valCurs = new ValCurs();
        valCurs.setDate(String.valueOf(VALID_DATE));

        var currencyCurs = new CurrencyCurs();
        currencyCurs.setRefreshDate(VALID_DATE);

        var expected = new CurrencyResponse();
        expected.setRequestDate(VALID_DATE);
        expected.setRefreshDate(VALID_DATE);

        //when
        when(currencyCursRepository.findCurrencyCursByRefreshDate(VALID_DATE))
                .thenReturn(Optional.empty());
        when(client.getRatesByDate(VALID_DATE)).thenReturn(valCurs);
        when(currencyMapper.toCurrencyCurs(valCurs)).thenReturn(currencyCurs);
        when(currencyCursRepository.save(currencyCurs)).thenReturn(currencyCurs);
        when(currencyMapper.toCurrencyResponse(currencyCurs, VALID_DATE)).thenReturn(expected);

        //then
        var actual = currencyService.getAllByDate(VALID_DATE);

        assertEquals(expected, actual);
        verify(currencyCursRepository, times(1)).findCurrencyCursByRefreshDate(VALID_DATE);
        verify(client, times(1)).getRatesByDate(VALID_DATE);
        verify(currencyMapper, times(1)).toCurrencyCurs(valCurs);
        verify(currencyCursRepository, times(1)).save(currencyCurs);
        verify(currencyMapper, times(1)).toCurrencyResponse(currencyCurs, VALID_DATE);
    }

    @Test
    void getAllByCode_Success() {
        //given
        var code = "TRY";
        var currency = new Currency();
        currency.setCode(code);
        List<Currency> currencies = List.of(currency);
        var currencyDto = new CurrencyDto();
        currencyDto.setCode(code);
        List<CurrencyDto> expected = List.of(currencyDto);

        //when
        when(currencyRepository.findAllByCode(code)).thenReturn(currencies);
        when(currencyMapper.toCurrencies(currencies)).thenReturn(expected);

        //then
        var actual = currencyService.getAllByCode(code);

        assertEquals(expected, actual);

        verify(currencyRepository, times(1)).findAllByCode(code);
        verify(currencyMapper, times(1)).toCurrencies(currencies);
    }

    @Test
    void deleteAllByDate_Success() {
        //given
        var currencyCurs = new CurrencyCurs();
        currencyCurs.setRefreshDate(VALID_DATE);

        //when
        when(currencyCursRepository.findCurrencyCursByRefreshDate(VALID_DATE))
                .thenReturn(Optional.of(currencyCurs));
        doNothing().when(currencyCursRepository).delete(currencyCurs);

        //then
        currencyService.deleteAllByDate(VALID_DATE);

        verify(currencyCursRepository, times(1))
                .findCurrencyCursByRefreshDate(VALID_DATE);
        verify(currencyCursRepository, times(1)).delete(currencyCurs);
    }

    @Test
    void deleteAllByDate_WhenCurrencyCursNotFound_ShouldThrowServiceException() {
        //given
        LocalDate date = LocalDate.of(2022, 7, 25);
        var given = DateUtil.exceptWeekend(date);
        var currencyCurs = new CurrencyCurs();
        currencyCurs.setRefreshDate(given);

        //when
        when(currencyCursRepository.findCurrencyCursByRefreshDate(given))
                .thenReturn(Optional.empty());

        //then
        ServiceException actual = assertThrows(ServiceException.class,
                () -> currencyService.deleteAllByDate(date));

        assertEquals(CURRENCY_CURS_NOT_FOUND.code(), actual.getErrorCode());
        verify(currencyCursRepository, times(1)).findCurrencyCursByRefreshDate(given);
        verify(currencyCursRepository, never()).delete(currencyCurs);
    }

}
