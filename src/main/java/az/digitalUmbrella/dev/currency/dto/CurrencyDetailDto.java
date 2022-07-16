package az.digitalUmbrella.dev.currency.dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyDetailDto {

    private List<CurrencyDto> currencies;
    private String type;

}
