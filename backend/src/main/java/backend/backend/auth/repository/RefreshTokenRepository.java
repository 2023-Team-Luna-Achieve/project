package backend.backend.auth.repository;

import backend.backend.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteAllByUserId(Long id);
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
