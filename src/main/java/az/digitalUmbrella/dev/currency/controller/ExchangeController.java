package az.digitalUmbrella.dev.currency.controller;

import az.digitalUmbrella.dev.currency.dto.ExchangeDto;
import az.digitalUmbrella.dev.currency.dto.ExchangeSearchFilter;
import az.digitalUmbrella.dev.currency.dto.response.ExchangeResponse;
import az.digitalUmbrella.dev.currency.error.validation.Currency;
import az.digitalUmbrella.dev.currency.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;


    @GetMapping("/date/{date}")
    public ExchangeResponse getAllByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @PathVariable LocalDate date) {
        return exchangeService.getAllByDate(date);
    }

    @GetMapping("/to/{to}")
    public ExchangeResponse getAllByCode(@Currency @PathVariable String to) {
        return exchangeService.getAllByCode(to);
    }

    @GetMapping
    public ExchangeDto getByDateAndCode(@Valid ExchangeSearchFilter filter) {
        return exchangeService.getByDateAndCode(filter);
    }

}
