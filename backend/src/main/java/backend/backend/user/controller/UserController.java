package backend.backend.user.controller;

import backend.backend.user.dto.*;
import backend.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final HttpSession session;


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest, BCryptPasswordEncoder encoder) {
        return userService.createUserIfEmailNotExists(signUpRequest, encoder);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok().body(userService.processSignIn(signInRequest, session));
    }

    @PostMapping("/signout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> signOut() {
        return ResponseEntity.ok().body(userService.processSignOut(session));
    }

    @GetMapping("/login-confirm")
    public ResponseEntity<String> confirm() {
        return ResponseEntity.ok().body(userService.loginConfirm(session));
    }
}