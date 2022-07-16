package az.digitalUmbrella.dev.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyDto {

    private String code;
    private String name;
    private BigDecimal value;
    private String nominal;

}
