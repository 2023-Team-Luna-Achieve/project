package backend.backend.common;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.AuthProvider;
import backend.backend.user.entity.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 1L;

    String name() default "TestUser";

    String email() default "testUser@naver.com";

    String password() default "testUser";

    Role role() default Role.ROLE_USER;

    Affiliation affiliation() default Affiliation.Techeer;

    AuthProvider provider() default AuthProvider.local;

    String providerId() default "providerId";
}
