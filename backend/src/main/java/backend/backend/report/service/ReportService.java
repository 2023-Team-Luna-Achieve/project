package backend.backend.report.service;

import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.common.exception.ReportException;
import backend.backend.report.domain.Report;
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

    public Long makeReport(User currentUser, ReportRequest reportRequest) {
        User reporter = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        User reportedUser = userRepository.findByEmail(reportRequest.reportedUserEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (reportRepository.existsByReporterIdAndReportedUserId(reporter.getId(), reportedUser.getId())) {
            throw new ReportException(ErrorCode.ALREADY_REPORT_EXIST);
        }

        return reportRepository.save(reportRequest.toEntity(reporter, reportedUser)).getId();
    }

    public void blockUser(User currentUser, ReportRequest reportRequest) {
        User reporter = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        User reportedUser = userRepository.findByEmail(reportRequest.reportedUserEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Report report = reportRepository.findReportByReporterIdAndReportedUserId(reporter.getId(), reportedUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.REPORT_NOT_FOUND));

        report.changeStatusToBlockUser();
    }

    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
