package backend.backend.report.repository;

import backend.backend.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
