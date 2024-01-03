package backend.backend.auth.service;

import backend.backend.auth.domain.RefreshToken;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.repository.RefreshTokenRepository;
import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundRefreshTokenException;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveRefreshToken(String refreshToken, String email) {
        User user = userRepository.findUserByEmail(email);
        deleteRefreshToken(email);
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));
    }

    @Transactional
    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteAllByEmail(email);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findRefreshTokenByToken(refreshToken)
                .orElseThrow(() -> new NotFoundRefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        tokenProvider.validateToken(token.getToken());
    }
}
