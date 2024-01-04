package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    @Query(value = "SELECT nb FROM NoticeBoard nb " +
            " JOIN fetch nb.user " +
            " WHERE nb.id < :lastNoticeBoardId" +
            " order by nb.id desc",
            countQuery = "SELECT count(nb) from NoticeBoard nb where nb.id < :lastNoticeBoardId")
    Page<NoticeBoard> findAllByIdLessThanOrderByIdDesc(@Param("lastNoticeBoardId") Long lastNoticeBoardId, PageRequest pageRequest);

    @Query("SELECT nb FROM NoticeBoard nb LEFT JOIN FETCH nb.user WHERE nb.id = :id")
    Optional<NoticeBoard> findByIdWithUsername(@Param("id") Long id);
}
