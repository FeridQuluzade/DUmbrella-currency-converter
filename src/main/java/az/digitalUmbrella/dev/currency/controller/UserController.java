package az.digitalUmbrella.dev.currency.controller;

import az.digitalUmbrella.dev.currency.dto.request.UserCreateRequest;
import az.digitalUmbrella.dev.currency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void saveUser(@Valid @RequestBody UserCreateRequest user) {
        userService.save(user);
    }

}