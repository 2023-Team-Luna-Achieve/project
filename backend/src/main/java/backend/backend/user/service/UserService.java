package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.repository.RefreshTokenRepository;
import backend.backend.common.exception.AuthenticationException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.UnVerifiedAccountException;
import backend.backend.user.dto.*;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    private Long create(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional
    public Long createUserIfEmailNotExists(SignUpRequest signUpRequest) {
        if (userRepository.findUserByEmail(signUpRequest.getEmail()) == null) {
            log.info("email: {}", redisUtil.getData(signUpRequest.getEmail()));

            String emailAuthStatus = redisUtil.getData(signUpRequest.getEmail());

            if (emailAuthStatus == null || !emailAuthStatus.equals("verified")) {
                throw new UnVerifiedAccountException(ErrorCode.UNAUTHORIZED_EMAIL);
            }

            signUpRequest = signUpRequest.encryptPassword(passwordEncoder);
            User user = signUpRequest.toEntity();
            return create(user);
        }
        throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL);
    }

    @Transactional
    public void signOut(User user) {
        refreshTokenRepository.deleteAllByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserInfo(User user) {
        User loggesdUser = findUserById(user.getId());
        return UserResponse.from(loggesdUser);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유저 정보 없음"));
    }
}
