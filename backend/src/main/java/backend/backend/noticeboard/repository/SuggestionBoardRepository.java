package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.SuggestionBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SuggestionBoardRepository extends JpaRepository<SuggestionBoard, Long> {
    Optional<SuggestionBoard> findById(Long id);

    @Query("SELECT n FROM SuggestionBoard n WHERE n.id < :offset ORDER BY n.id DESC")
    Page<SuggestionBoard> findAllByCursor(Pageable pageable, @Param("offset") Long offset);

    Page<SuggestionBoard> findAll(Pageable pageable);

    Page<SuggestionBoard> findSuggestionsByIdGreaterThan(Long id, Pageable pageable);

    Page<SuggestionBoard> findSuggestionsByIdLessThan(Long id, Pageable pageable);
}
