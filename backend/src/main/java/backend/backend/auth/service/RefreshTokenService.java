package backend.backend.auth.service;

import backend.backend.auth.domain.RefreshToken;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.repository.RefreshTokenRepository;
import backend.backend.common.exception.*;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveRefreshToken(String refreshToken, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        deleteRefreshToken(user.getId());
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));
    }

    public void deleteRefreshToken(Long id) {
        refreshTokenRepository.deleteAllByUserId(id);
    }

    public void validateRefreshToken(String refreshToken) {
        RefreshToken token = findRefreshToken(refreshToken);
        if (!tokenProvider.validateToken(token.getToken())) {
            throw new RefreshTokenException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    public User findRefreshTokenOwner(String refreshToken) {
        RefreshToken token = findRefreshToken(refreshToken);
        return userRepository.findById(token.getUserId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.USER_NOT_FOUND)
        );
    }

    private RefreshToken findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findRefreshTokenByToken(refreshToken)
                .orElseThrow(() -> new NotFoundRefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }
}
