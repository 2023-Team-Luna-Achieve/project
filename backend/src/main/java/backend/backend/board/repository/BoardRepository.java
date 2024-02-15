package backend.backend.board.repository;

import backend.backend.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("SELECT nb FROM Board nb LEFT JOIN FETCH nb.user WHERE nb.id = :id")
    Optional<Board> findByIdWithUsername(@Param("id") Long id);
}
