package backend.backend.report.repository;

import backend.backend.report.domain.Report;
import backend.backend.report.domain.ReportedUserGeneratedCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByReporterIdAndReportedUserGeneratedCategoryAndTargetId(Long reporterId, ReportedUserGeneratedCategory reportedUserGeneratedCategory, Long targetId);
}