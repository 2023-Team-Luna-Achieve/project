package backend.backend.auth.controller;

import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.auth.jwt.UserAdapter;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.user.dto.SignInRequest;
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

import javax.validation.Valid;

@Api(tags = "Auth API" , description = "로그인")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
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
        String jwt = tokenProvider.createToken(authentication);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();
    }
}
