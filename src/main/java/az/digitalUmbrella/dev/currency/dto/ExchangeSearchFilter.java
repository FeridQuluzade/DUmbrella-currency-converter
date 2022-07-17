package az.digitalUmbrella.dev.currency.dto;

import az.digitalUmbrella.dev.currency.error.validation.Currency;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static az.digitalUmbrella.dev.currency.error.validation.ErrorMessages.INVALID_DATE;

@Data
public class ExchangeSearchFilter {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = INVALID_DATE)
    private LocalDate date;

    @Currency
    private String from;

    public void setFrom(String from) {
        this.from = from.toUpperCase();
    }

}
