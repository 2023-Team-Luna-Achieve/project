package backend.backend.report.service;

import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.common.exception.ReportException;
import backend.backend.report.domain.ReportCategory;
import backend.backend.report.dto.ReportRequest;
import backend.backend.report.repository.ReportRepository;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public Long createReport(User currentUser, ReportRequest reportRequest) {
        User reporter = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));


        reportRepository.findReportByReporterIdAndReportCategoryAndTargetId(reporter.getId(), reportRequest.reportCategory(), reportRequest.targetId())
                .ifPresent(present -> {
                    if (present.getReportCategory().equals(ReportCategory.BOARD)) {
                        throw new ReportException(ErrorCode.ALREADY_BOARD_REPORT_EXIST);
                    }

                    throw new ReportException(ErrorCode.ALREADY_COMMENT_REPORT_EXIST);
                });

        return reportRepository.save(reportRequest.toEntity(reporter)).getId();
    }

    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
