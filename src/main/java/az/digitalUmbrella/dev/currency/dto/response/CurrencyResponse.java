package az.digitalUmbrella.dev.currency.dto.response;

import az.digitalUmbrella.dev.currency.dto.CurrencyDetailDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CurrencyResponse {

    private List<CurrencyDetailDto> currencyDetails;
    private LocalDate refreshDate;
    private LocalDate requestDate;

}
