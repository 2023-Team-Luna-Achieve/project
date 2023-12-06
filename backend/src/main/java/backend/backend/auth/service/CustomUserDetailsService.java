package backend.backend.auth.service;

import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String email) {

        return userRepository.findOneWithAuthoritiesByEmail(email)
                .map(user -> {
                    return createUser(email, user);
                })
                .orElseThrow(() -> {
                    return new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
                });
    }

    private org.springframework.security.core.userdetails.User createUser(String email, User user) {
        Collection<? extends GrantedAuthority> grantedAuthorities =
                Collections.singleton(new SimpleGrantedAuthority(user.roleName()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}

