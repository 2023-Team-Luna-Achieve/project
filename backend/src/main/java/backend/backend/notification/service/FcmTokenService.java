package backend.backend.notification.service;

import backend.backend.notification.domain.FcmToken;
import backend.backend.notification.dto.FcmAccessTokenCreateRequest;
import backend.backend.notification.repository.FcmTokenRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {
    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public void save(User user, FcmAccessTokenCreateRequest request) {
        fcmTokenRepository.findByUserId(user.getId()).ifPresentOrElse(
                fcmToken -> fcmToken.update(request.token()),
                () -> fcmTokenRepository.save(FcmToken.createFcmToken(user.getId(), request.token()))
        );
    }
}
