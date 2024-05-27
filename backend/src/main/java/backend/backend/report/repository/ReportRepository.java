package backend.backend.report.repository;

import backend.backend.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);
    Optional<Report> findReportByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);
}
