package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    @Query("SELECT n FROM NoticeBoard n WHERE n.id < :cursor ORDER BY n.id DESC")
    Page<NoticeBoard> findAllByCursor(Pageable pageable, @Param("cursor") Long cursor);
}

    Page<NoticeBoard> findAllByIdLessThanOrderByIdDesc(Long lastNoticeBoardId, PageRequest pageRequest);
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
}
