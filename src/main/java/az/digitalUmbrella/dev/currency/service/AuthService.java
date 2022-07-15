package az.digitalUmbrella.dev.currency.service;

import az.digitalUmbrella.dev.currency.security.TokenProvider;
import az.digitalUmbrella.dev.currency.dto.request.LoginRequest;
import az.digitalUmbrella.dev.currency.dto.response.AuthTokenResponse;
import az.digitalUmbrella.dev.currency.error.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static az.digitalUmbrella.dev.currency.error.ErrorCodes.INVALID_USERNAME_OR_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;

    public AuthTokenResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword(),
                            userDetails.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return AuthTokenResponse.of(token);
        } catch (AuthenticationException exception) {
            throw ServiceException.of(
                    INVALID_USERNAME_OR_PASSWORD, "Invalid username or password!");
        }
    }

}
