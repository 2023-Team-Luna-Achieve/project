package backend.backend.report.service;

import backend.backend.board.repository.BoardRepository;
import backend.backend.comment.repository.CommentRepository;
import backend.backend.common.domain.UserGeneratedContent;
import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.common.exception.ReportException;
import backend.backend.report.domain.Report;
import backend.backend.report.domain.ReportedUserGeneratedCategory;
import backend.backend.report.domain.ReportStatus;
import backend.backend.report.dto.ReportRequest;
import backend.backend.report.dto.ReportStatusChangeRequest;
import backend.backend.report.repository.ReportRepository;
import backend.backend.user.entity.Role;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private static final int MINIMUM_REPORT_COUNT = 0;

    public Long createReport(User currentUser, ReportRequest reportRequest) {
        User reporter = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        throwExceptionIfCurrentUserAlreadyReportedSameUGC(reporter, reportRequest);
        throwExceptionIfUGCWriterIsAdmin(reportRequest);
        return reportRepository.save(reportRequest.toEntity(reporter)).getId();
    }

    private void throwExceptionIfCurrentUserAlreadyReportedSameUGC(User reporter, ReportRequest reportRequest) {
        reportRepository.findReportByReporterIdAndReportedUserGenerateContentCategoryAndTargetId(reporter.getId(), reportRequest.reportedUserGenerateContentCategory(), reportRequest.targetId())
                .ifPresent(present -> {
                    if (present.getReportedUserGenerateContentCategory().equals(ReportedUserGeneratedCategory.BOARD)) {
                        throw new ReportException(ErrorCode.ALREADY_BOARD_REPORT_EXIST);
                    }

                    throw new ReportException(ErrorCode.ALREADY_COMMENT_REPORT_EXIST);
                });
    }

    private void throwExceptionIfUGCWriterIsAdmin(ReportRequest reportRequest) {
        UserGeneratedContent content = findUgcCategoryByReport(reportRequest.targetId(), reportRequest.reportedUserGenerateContentCategory());

        if (reportRequest.reportedUserGenerateContentCategory().equals(ReportedUserGeneratedCategory.BOARD)) {
            content = boardRepository.findById(reportRequest.targetId()).orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        }

        if (reportRequest.reportedUserGenerateContentCategory().equals(ReportedUserGeneratedCategory.COMMENT)) {
            content = commentRepository.findById(reportRequest.targetId()).orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        }

        if (content.getUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new AuthException(ErrorCode.ADMIN_REPORTING_NOT_ALLOWED);
        }
    }

    @Transactional
    public void updateReportStatus(Long reportId, ReportStatusChangeRequest reportStatusChangeRequest) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REPORT_NOT_FOUND));

        updateUserGenerateContentReportCount(report, reportStatusChangeRequest);
        report.updateStatus(reportStatusChangeRequest.reportStatus());
    }

    private void updateUserGenerateContentReportCount(Report report, ReportStatusChangeRequest reportStatusChangeRequest) {
        UserGeneratedContent content = findUgcCategoryByReport(report.getTargetId(), report.getReportedUserGenerateContentCategory());
        if (!isCurrentReportStatusAccept(report) && isReportStatusChangeRequestAccept(reportStatusChangeRequest)) {
            content.addReportCount();
        }

        if (isCurrentReportStatusAccept(report) && !isReportStatusChangeRequestAccept(reportStatusChangeRequest)) {
            if (content.getReportCount() >= MINIMUM_REPORT_COUNT) {
                content.minusReportCount();
            }
        }
    }

    private UserGeneratedContent findUgcCategoryByReport(Long targetId, ReportedUserGeneratedCategory ugcCategory) {
        if (ugcCategory.equals(ReportedUserGeneratedCategory.BOARD)) {
            return boardRepository.findById(targetId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        }

        return commentRepository.findById(targetId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }

    private boolean isCurrentReportStatusAccept(Report report) {
        return report.getStatus().equals(ReportStatus.ACCEPT);
    }

    private boolean isReportStatusChangeRequestAccept(ReportStatusChangeRequest reportStatusChangeRequest) {
        return reportStatusChangeRequest.reportStatus().equals(ReportStatus.ACCEPT);
    }
}
