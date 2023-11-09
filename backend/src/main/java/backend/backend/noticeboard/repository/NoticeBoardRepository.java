package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
}
