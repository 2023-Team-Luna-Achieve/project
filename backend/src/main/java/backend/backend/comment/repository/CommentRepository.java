package backend.backend.comment.repository;

import backend.backend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> , CommentRepositoryCustom {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.board.id = :boardId")
    List<Comment> findAllByBoardId(@Param("boardId") Long boardId);
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
}