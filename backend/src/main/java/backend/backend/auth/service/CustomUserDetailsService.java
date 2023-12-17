package backend.backend.auth.service;

import backend.backend.auth.jwt.UserAdapter;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String email) {
        return userRepository.findOneWithAuthoritiesByEmail(email)
                .map(UserAdapter::new)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
                });
    }
}
