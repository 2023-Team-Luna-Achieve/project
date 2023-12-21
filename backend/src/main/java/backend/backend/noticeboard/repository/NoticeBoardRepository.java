package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    @Query("SELECT n FROM NoticeBoard n WHERE n.id < :cursor ORDER BY n.id DESC")
    Page<NoticeBoard> findAllByCursor(Pageable pageable, @Param("cursor") Long cursor);
}
