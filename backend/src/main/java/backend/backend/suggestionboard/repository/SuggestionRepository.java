package backend.backend.suggestionboard.repository;

import backend.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<Suggestion,Long>{

    Optional<Blog> findById(Long id);
    Page<Blog> findByUserId(Long userId, Pageable pageable);
}
