package backend.backend.user.controller;

import backend.backend.user.dto.*;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.createUserIfEmailNotExists(signUpRequest);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return userService.processSignIn(signInRequest);
    }
}