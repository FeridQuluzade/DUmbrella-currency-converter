package az.digitalUmbrella.dev.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeDto {

    private String code;
    private String name;
    private String nominal;
    private BigDecimal value;

}
