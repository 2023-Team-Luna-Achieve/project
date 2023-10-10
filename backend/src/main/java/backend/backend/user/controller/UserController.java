package backend.backend.user.controller;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.service.EmailService;
import backend.backend.user.dto.SignUpRequest;
import backend.backend.user.dto.SignUpResponse;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/api/users")
public class UserController {
    private final UserService userService;
    private final RedisUtil redisUtil;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.createUserIfEmailNotExists(signUpRequest);
    }
}
