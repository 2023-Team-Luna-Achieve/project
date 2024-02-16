package backend.backend.common;

import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .id(customUser.id())
                .name(customUser.name())
                .role(customUser.role())
                .email(customUser.email())
                .affiliation(customUser.affiliation())
                .password(customUser.password())
                .provider(customUser.provider())
                .providerId(customUser.providerId())
                .build();

        CustomUserDetails userDetails = CustomUserDetails.create(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),
                userDetails.getAuthorities());
        context.setAuthentication(authentication);
        return context;
    }
}
