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
        User user = new User();
        user.setId(customUser.id());
        user.setName(customUser.name());
        user.setRole(customUser.role());
        user.setEmail(customUser.email());
        user.setAffiliation(customUser.affiliation());
        user.setPassword(customUser.password());
        user.setProvider(customUser.provider());
        user.setProviderId(customUser.providerId());

        CustomUserDetails userDetails = CustomUserDetails.create(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),
                userDetails.getAuthorities());
        context.setAuthentication(authentication);
        return context;
    }
}
