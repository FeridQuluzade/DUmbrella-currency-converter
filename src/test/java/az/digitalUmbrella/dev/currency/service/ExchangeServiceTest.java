package az.digitalUmbrella.dev.currency.service;

import az.digitalUmbrella.dev.currency.dto.CurrencyDetailDto;
import az.digitalUmbrella.dev.currency.dto.CurrencyDto;
import az.digitalUmbrella.dev.currency.dto.ExchangeDto;
import az.digitalUmbrella.dev.currency.dto.response.CurrencyResponse;
import az.digitalUmbrella.dev.currency.dto.response.ExchangeResponse;
import az.digitalUmbrella.dev.currency.mapper.ExchangeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private ExchangeMapper exchangeMapper;

    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    void getAllByDate_Success() {
        //given
        var date = LocalDate.of(2022, 7, 25);
        var currencyDetail = new CurrencyDetailDto();
        var currencyDetails = List.of(currencyDetail);
        var currencyResponse = new CurrencyResponse();
        currencyResponse.setRefreshDate(date);
        currencyResponse.setCurrencyDetails(currencyDetails);
        var exchangeDto = new ExchangeDto();
        var exchanges = List.of(exchangeDto);

        var expected = ExchangeResponse.of(exchanges);

        //when
        when(currencyService.getAllByDate(date)).thenReturn(currencyResponse);
        when(exchangeMapper.toExchangesFromCurrencyDetail(currencyDetails)).thenReturn(exchanges);

        //then
        var actual = exchangeService.getAllByDate(date);

        assertEquals(expected, actual);
        verify(currencyService, times(1)).getAllByDate(date);
        verify(exchangeMapper, times(1)).toExchangesFromCurrencyDetail(currencyDetails);
    }

    @Test
    void getAllByCode_Success() {
        //given
        var code = "AZN";
        var currencyDto = new CurrencyDto();
        currencyDto.setCode(code);
        var currencies = List.of(currencyDto);
        var exchangeDto = new ExchangeDto();
        exchangeDto.setCode(code);
        var exchanges = List.of(exchangeDto);
        var expected = ExchangeResponse.of(exchanges);

        //when
        when(currencyService.getAllByCode(code)).thenReturn(currencies);
        when(exchangeMapper.toExchanges(currencies)).thenReturn(exchanges);

        //then
        var actual = exchangeService.getAllByCode(code);

        assertEquals(expected, actual);
        verify(currencyService, times(1)).getAllByCode(code);
        verify(exchangeMapper, times(1)).toExchanges(currencies);
    }

}
