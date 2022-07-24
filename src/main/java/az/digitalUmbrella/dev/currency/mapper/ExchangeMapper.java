package az.digitalUmbrella.dev.currency.mapper;

import az.digitalUmbrella.dev.currency.dto.CurrencyDetailDto;
import az.digitalUmbrella.dev.currency.dto.CurrencyDto;
import az.digitalUmbrella.dev.currency.dto.ExchangeDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ExchangeMapper {

    ExchangeMapper INSTANCE = Mappers.getMapper(ExchangeMapper.class);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<ExchangeDto> toExchanges(List<CurrencyDto> currencies);

    default List<ExchangeDto> toExchangesFromCurrencyDetail(List<CurrencyDetailDto> currencies) {
        List<ExchangeDto> exchangeDtoList = new ArrayList<>();
        for (CurrencyDetailDto currencyDetail : currencies) {
            exchangeDtoList.addAll(toExchanges(currencyDetail.getCurrencies()));
        }
        return exchangeDtoList;
    }

}
