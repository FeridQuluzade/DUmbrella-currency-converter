package az.digitalUmbrella.dev.currency.dto.request;

import az.digitalUmbrella.dev.currency.error.validation.ErrorMessages;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateRequest {

    @NotBlank(message = ErrorMessages.USERNAME_NOT_DEFINED)
    private String username;

    @NotBlank(message = ErrorMessages.PASSWORD_NOT_DEFINED)
    private String password;

}
