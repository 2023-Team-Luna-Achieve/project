package backend.backend.noticeboard.repository;

import backend.backend.noticeboard.entity.Notice;

public class NoticeRepository {
    public abstract static class NoticeRepository extends JpaRepository<Notice, Long> {
    }
}
