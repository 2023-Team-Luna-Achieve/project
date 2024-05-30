package backend.backend.report.repository;

import backend.backend.report.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    boolean existsByBlockerIdAndBlockedUserId(Long reporterId, Long reportedUserId);
    Optional<Block> findReportByBlockerIdAndBlockedUserId(Long reporterId, Long reportedUserId);
}
