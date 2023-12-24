package backend.backend.user.controller;

import backend.backend.user.dto.*;
import backend.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User API", description = "회원가입, 로그아웃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입 API", notes = "회원가입 이전에 이메일 인증을 먼저 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest, BCryptPasswordEncoder encoder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserIfEmailNotExists(signUpRequest, encoder));
    }

    @PostMapping("/sign-out")
    @ApiOperation(value = "로그아웃 API", notes = "로그아웃을 진행한다")
    public ResponseEntity<String> signOut() {
        return ResponseEntity.ok().body("로그아웃 되었습니다."); // jwt 변경 후 임시 로그아웃
    }
}