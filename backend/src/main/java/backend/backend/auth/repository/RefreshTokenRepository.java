package backend.backend.auth.repository;

import backend.backend.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public void deleteAllByEmail(String email);

    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
