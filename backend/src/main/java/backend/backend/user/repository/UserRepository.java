package backend.backend.user.repository;

import backend.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User>  findUserByEmail(String email);

    User findUserByEmail(String email);

    Optional<User> findOneWithAuthoritiesByEmail(String email);


}
