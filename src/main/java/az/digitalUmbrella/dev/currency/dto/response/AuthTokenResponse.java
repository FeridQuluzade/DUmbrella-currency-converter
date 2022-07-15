package az.digitalUmbrella.dev.currency.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenResponse {

    private String token;

    public static AuthTokenResponse of(String token) {
        return new AuthTokenResponse(token);
    }

}