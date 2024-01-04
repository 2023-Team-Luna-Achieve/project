package backend.backend.user.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.user.dto.*;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Api(tags = "User API", description = "회원가입, 로그아웃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입 API", notes = "회원가입 이전에 이메일 인증을 먼저 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest signUpRequest, BCryptPasswordEncoder encoder) {
        Long userId = userService.createUserIfEmailNotExists(signUpRequest, encoder);
        return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
    }

    @PostMapping("/sign-out")
    @ApiOperation(value = "로그아웃 API", notes = "로그아웃을 진행한다")
    public ResponseEntity<String> signOut(@CurrentUser User user) {
        userService.signOut(user);
        return ResponseEntity.ok().body("로그아웃 되었습니다."); // jwt 변경 후 임시 로그아웃
    }
}