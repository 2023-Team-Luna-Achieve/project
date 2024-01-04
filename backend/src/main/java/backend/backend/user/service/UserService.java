package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.repository.RefreshTokenRepository;
import backend.backend.exception.*;
import backend.backend.user.dto.*;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private Long create(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional
    public Long createUserIfEmailNotExists(SignUpRequest signUpRequest, BCryptPasswordEncoder encoder) {
        if (userRepository.findUserByEmail(signUpRequest.getEmail()) == null) {
            if (redisUtil.getData(signUpRequest.getEmail()) == null) {
                throw new UnVerifiedAccountException(ErrorCode.UNAUTHORIZED_EMAIL);

            } else if (redisUtil.getData(signUpRequest.getEmail()).equals("verified")) {
                signUpRequest = signUpRequest.encryptPassword(encoder);
                User user = signUpRequest.toEntity();
                return create(user);
            }
        }
        throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL);
    }

    public void signOut(User user) {
        refreshTokenRepository.deleteAllByUserId(user.getId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유저 정보 없음"));
    }
}
