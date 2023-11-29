package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findById(Long id);

    Page<Suggestion> findAll(Pageable pageable);
    Page<Suggestion> findSuggestionsByIdGreaterThan(Long id, Pageable pageable);

    Page<Suggestion> findSuggestionsByIdLessThan(Long id, Pageable pageable);
}
