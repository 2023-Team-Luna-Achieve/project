package backend.backend.comment.repository;

import backend.backend.comment.entity.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Long> {

    @Query("SELECT c FROM NoticeBoardComment c JOIN FETCH c.user WHERE c.noticeBoard.id = :noticeBoardId")
    List<NoticeBoardComment> findAllByNoticeBoardId(@Param("noticeBoardId") Long noticeBoardId);
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
}