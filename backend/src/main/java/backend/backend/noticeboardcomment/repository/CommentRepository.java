package backend.backend.noticeboardcomment.repository;

import backend.backend.noticeboardcomment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByNoticeBoardId(Long noticeBoardId);
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
}