package backend.backend.user.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.auth.jwt.filter.JwtExtractUtil;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.service.RefreshTokenService;
import backend.backend.user.dto.*;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Api(tags = "User API", description = "회원가입, 로그아웃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtExtractUtil jwtExtractUtil;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation(value = "회원가입 API", notes = "회원가입 이전에 이메일 인증을 먼저 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        Long userId = userService.createUserIfEmailNotExists(signUpRequest);
        return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
    }

    @ApiOperation(value = "로그인 API", notes = "로그인을 진행한다. (헤더로 보낸 jwt 토큰 확인)")
    @PostMapping("/sign-in")
    public ResponseEntity<String> authorize(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = settingAuthentication(signInRequest.getEmail(), signInRequest.getPassword());
        String accessToken = tokenProvider.createAccessToken(authentication);

        String refreshToken = tokenProvider.createRefreshToken();

        refreshTokenService.saveRefreshToken(refreshToken, signInRequest.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("Refresh-Token", "Bearer " + refreshToken)
                .build();
    }

    @PostMapping("/sign-out")
    @ApiOperation(value = "로그아웃 API", notes = "로그아웃을 진행한다")
    public ResponseEntity<Void> signOut(@CurrentUser User user) {
        userService.signOut(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "access 토큰 재발급 API",
            notes = "access 토큰 만료시 refresh 토큰을 사용하여 엑세스 토큰을 재발급 한다. " +
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

    private Authentication settingAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        authenticationToken.setDetails(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}