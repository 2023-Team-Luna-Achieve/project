package backend.backend.notification.repository;

import backend.backend.notification.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
