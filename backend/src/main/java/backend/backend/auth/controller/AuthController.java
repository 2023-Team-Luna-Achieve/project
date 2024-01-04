package backend.backend.auth.controller;

import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.auth.jwt.filter.JwtExtractUtil;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.service.RefreshTokenService;
import backend.backend.user.dto.SignInRequest;
import backend.backend.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "Auth API" , description = "로그인")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final JwtExtractUtil jwtExtractUtil;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @ApiOperation(value = "access 토큰 재발급 API",
            notes = "access 토큰 만료시 refresh 토큰을 사용하여 엑세스 토큰을 재발급 한다. " +
                    "refresh 토큰또한 만료된 경우 다시 로그인한다. ")
    @PostMapping("/refresh")
    public ResponseEntity<String> getAccessTokenUsingRefresh(HttpServletRequest request) {
        String refreshToken = jwtExtractUtil.resolveRefreshToken(request);
        refreshTokenService.validateRefreshToken(refreshToken);
        User refreshTokenOwner = refreshTokenService.findRefreshTokenOwner(refreshToken);

        Authentication authentication = settingAuthentication(refreshTokenOwner.getEmail(), refreshTokenOwner.getPassword());
        String accessToken = tokenProvider.createAccessToken(authentication);
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
