package backend.backend.user.repository;

import backend.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
