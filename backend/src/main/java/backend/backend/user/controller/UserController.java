package backend.backend.user.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.auth.jwt.filter.JwtExtractUtil;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.service.RefreshTokenService;
import backend.backend.common.exception.AuthenticationException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.user.dto.*;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;

@Tag(name = "User API", description = "회원가입, 로그아웃")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtExtractUtil jwtExtractUtil;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Operation(tags = "회원가입 API", description = "회원가입 이전에 이메일 인증을 먼저 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.deletedAccountVerification(signUpRequest.email());
        Long userId = userService.createUserIfEmailNotExists(signUpRequest);
        return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
    }

    @Operation(tags = "로그인 API", description = "로그인을 진행한다. (헤더로 보낸 jwt 토큰 확인)")
    @PostMapping("/sign-in")
    public ResponseEntity<Void> authorize(@Valid @RequestBody SignInRequest signInRequest) {
        userService.deletedAccountVerification(signInRequest.email());
        Authentication authentication = settingAuthentication(signInRequest.email(), signInRequest.password());
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken();

        refreshTokenService.saveRefreshToken(refreshToken, signInRequest.email());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("Refresh-Token", "Bearer " + refreshToken)
                .build();
    }

    @Operation(tags = "로그아웃 API", description = "로그아웃을 진행한다")
    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(@CurrentUser User user) {
        userService.signOut(user);
        return ResponseEntity.ok().build();
    }

    @Operation(tags = "access 토큰 재발급 API",
            description = "access 토큰 만료시 refresh 토큰을 사용하여 엑세스 토큰을 재발급 한다. " +
                    "refresh 토큰또한 만료된 경우 다시 로그인한다. ")
    @PostMapping("/refresh")
    public ResponseEntity<String> getAccessTokenUsingRefresh(HttpServletRequest request) {
        String refreshToken = jwtExtractUtil.resolveRefreshToken(request);
        refreshTokenService.validateRefreshToken(refreshToken);
        User refreshTokenOwner = refreshTokenService.findRefreshTokenOwner(refreshToken);

        String accessToken = tokenProvider.createAccessTokenByRefreshToken(refreshTokenOwner);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();
    }

    @Operation(tags = "로그인된 유저 정보 API", description = "현재 유저 정보를 반환한다.. ")
    @GetMapping("/info")
    public ResponseEntity<UserResponse> getAccessTokenUsingRefresh(@CurrentUser User user) {
        return ResponseEntity.ok().body(userService.getUserInfo(user));
    }

    private Authentication settingAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        authenticationToken.setDetails(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @PatchMapping("/name")
    public ResponseEntity<Void> updateName(@CurrentUser User user,
                           @RequestBody NameUpdateRequest nameUpdateRequest) {
        userService.updateName(user, nameUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@CurrentUser User user,
                                               @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        userService.updatePassword(user, passwordUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Void> resetPasswordBeforeLogin(@RequestBody PasswordResetRequest passwordResetRequest) {
        userService.resetPassword(passwordResetRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/delete-account")
    public ResponseEntity<Void> deleteAccount(@CurrentUser User user) {
        userService.deleteAccount(user);
        return ResponseEntity.ok().build();
    }
}