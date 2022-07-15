package az.digitalUmbrella.dev.currency.controller;

import az.digitalUmbrella.dev.currency.dto.request.LoginRequest;
import az.digitalUmbrella.dev.currency.dto.response.AuthTokenResponse;
import az.digitalUmbrella.dev.currency.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public AuthTokenResponse generateToken(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
