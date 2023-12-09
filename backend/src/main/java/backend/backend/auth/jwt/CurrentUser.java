package backend.backend.auth.jwt;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal //(expression = "#this == 'anonymousUser' ? null : user")
public @interface CurrentUser {
}