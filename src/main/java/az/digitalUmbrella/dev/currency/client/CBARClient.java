package az.digitalUmbrella.dev.currency.client;

import az.digitalUmbrella.dev.currency.client.model.ValCurs;
import az.digitalUmbrella.dev.currency.error.ErrorCodes;
import az.digitalUmbrella.dev.currency.error.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CBARClient {

    @Value("${client.cbar.url}")
    private String url;

    @Value("${client.cbar.type}")
    private String type;
    private final RestTemplate restTemplate;

    @GetMapping
    public ValCurs getRatesByDate(LocalDate date) {
        try {
            return restTemplate.getForObject(getUrl(date), ValCurs.class);
        } catch (Exception ex) {
            throw ServiceException.of(ErrorCodes.UNKNOWN_ERROR_CODE, "Internal server error");
        }
    }

    private String getUrl(LocalDate date) {
        return String.format("%s/%s.%s", url, getValidDateForClient(date), type);
    }

    private static String getValidDateForClient(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
