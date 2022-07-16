package az.digitalUmbrella.dev.currency.controller;

import az.digitalUmbrella.dev.currency.dto.response.CurrencyResponse;
import az.digitalUmbrella.dev.currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static az.digitalUmbrella.dev.currency.error.validation.ErrorMessages.INVALID_DATE;

@Validated
@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/date/{date}")
    public CurrencyResponse getAllByDate(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @PastOrPresent(message = INVALID_DATE) @PathVariable LocalDate date) {
        return currencyService.getAllByDate(date);
    }

    @DeleteMapping("/date/{date}")
    public void deleteByDate(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @PastOrPresent(message = INVALID_DATE) @PathVariable LocalDate date) {
        currencyService.deleteAllByDate(date);
    }

}
