package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.repository.RefreshTokenRepository;
import backend.backend.common.exception.*;
import backend.backend.notification.repository.FcmTokenRepository;
import backend.backend.user.dto.*;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    private Long create(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional
    public Long createUserIfEmailNotExists(SignUpRequest signUpRequest) {
        if (!userRepository.existsByEmail(signUpRequest.email())) {
            String emailAuthStatus = redisUtil.getData(signUpRequest.email());

            if (emailAuthStatus == null || !emailAuthStatus.equals("verified")) {
                throw new UnVerifiedAccountException(ErrorCode.UNAUTHORIZED_EMAIL);
            }

            signUpRequest = signUpRequest.encryptPassword(passwordEncoder);
            User user = signUpRequest.toEntity();
            return create(user);
        }

        throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL);
    }

    public void deletedAccountVerification(String email) {
        userRepository.findUserByEmail(email).ifPresent(
                user -> {
                    if (user.isAccountDeleted()) {
                        throw new AuthException(ErrorCode.DELETED_ACCOUNT);
                    }
                }
        );
    }

    @Transactional
    public void signOut(User user) {
        refreshTokenRepository.deleteAllByUserId(user.getId());
        fcmTokenRepository.deleteAllByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserInfo(User user) {
        User loggesdUser = findUserById(user.getId());
        return UserResponse.from(loggesdUser);
    }

    @Transactional
    public void updatePassword(User currentUser, PasswordUpdateRequest passwordUpdateRequest) {
        User user = findUserById(currentUser.getId());
        updatePasswordIfCurrentPasswordCorrect(user, passwordUpdateRequest);
    }

    private void updatePasswordIfCurrentPasswordCorrect(User user, PasswordUpdateRequest passwordUpdateRequest) {
        if (!passwordEncoder.matches(passwordUpdateRequest.originalPassword(), user.getPassword())) {
            throw new AuthenticationException(ErrorCode.WRONG_PASSWORD);
        }

        String encodedRequestedPassword = passwordEncoder.encode(passwordUpdateRequest.requestPassword());
        user.updatePassword(encodedRequestedPassword);
    }


    @Transactional
    public void deleteAccount(User user) {
        User currentUser = findUserById(user.getId());
        refreshTokenRepository.deleteAllByUserId(user.getId());
        fcmTokenRepository.deleteAllByUserId(user.getId());
        currentUser.deleteAccount();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
