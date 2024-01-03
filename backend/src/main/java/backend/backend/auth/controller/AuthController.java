package backend.backend.auth.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.auth.jwt.UserAdapter;
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

    @ApiOperation(value = "로그인 API", notes = "로그인을 진행한다. (헤더로 보낸 jwt토큰 확인)")
    @PostMapping("/sign-in")
    public ResponseEntity<String> authorize(@Valid @RequestBody SignInRequest signInRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        authenticationToken.setDetails(principal);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken();

        refreshTokenService.saveRefreshToken(refreshToken, signInRequest.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("Refresh-Token", "Bearer " + refreshToken)
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> getAccessTokenUsingRefresh(HttpServletRequest request, Authentication authentication) {
        String refreshToken = jwtExtractUtil.resolveRefreshToken(request);
        refreshTokenService.validateRefreshToken(refreshToken);
        tokenProvider.createAccessToken(authentication);
        return null;
    }
}
