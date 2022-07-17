package az.digitalUmbrella.dev.currency.mapper;

import az.digitalUmbrella.dev.currency.client.model.ValCurs;
import az.digitalUmbrella.dev.currency.client.model.ValType;
import az.digitalUmbrella.dev.currency.client.model.Valute;
import az.digitalUmbrella.dev.currency.dto.CurrencyDto;
import az.digitalUmbrella.dev.currency.dto.response.CurrencyResponse;
import az.digitalUmbrella.dev.currency.mapper.qualifer.DateMapper;
import az.digitalUmbrella.dev.currency.model.Currency;
import az.digitalUmbrella.dev.currency.model.CurrencyCurs;
import az.digitalUmbrella.dev.currency.model.CurrencyDetail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

@Mapper(uses = DateMapper.class)
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    @Mapping(target = "currencies", source = "valutes")
    CurrencyDetail toCurrencyDetail(ValType valType);

    @Mapping(target = "currencyDetails", source = "valTypes")
    @Mapping(target = "refreshDate", source = "date")
    CurrencyCurs toCurrencyCurs(ValCurs valCurs);

    CurrencyResponse toCurrencyResponse(CurrencyCurs currencyCurs, LocalDate requestDate);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<CurrencyDto> toCurrencies(List<Currency> currencies);

}
