package backend.backend.comment.repository;

import backend.backend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> , CommentRepositoryCustom {

    @Query("SELECT MAX(c.sequenceNumber + 1) FROM Comment c WHERE c.board.id = :boardId")
    Optional<String> getMaxSequenceNumber(@Param("boardId") Long boardId);
}