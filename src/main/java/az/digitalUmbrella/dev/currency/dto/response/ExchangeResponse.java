package az.digitalUmbrella.dev.currency.dto.response;

import az.digitalUmbrella.dev.currency.dto.ExchangeDto;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponse {

    private List<ExchangeDto> exchanges;

    public static ExchangeResponse of(List<ExchangeDto> exchanges) {
        return new ExchangeResponse(exchanges);
    }

}
