package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.entity.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findById(Long id);

    @Query("SELECT n FROM Suggestion n WHERE n.id < :offset ORDER BY n.id DESC")
    Page<Suggestion> findAllByCursor(Pageable pageable, @Param("offset") Long offset);

    Page<Suggestion> findAll(Pageable pageable);

    Page<Suggestion> findSuggestionsByIdGreaterThan(Long id, Pageable pageable);

    Page<Suggestion> findSuggestionsByIdLessThan(Long id, Pageable pageable);
}
