package backend.backend.report.service;

import backend.backend.board.entity.Board;
import backend.backend.board.repository.BoardRepository;
import backend.backend.comment.entity.Comment;
import backend.backend.comment.repository.CommentRepository;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.common.exception.ReportException;
import backend.backend.report.domain.Report;
import backend.backend.report.domain.ReportCategory;
import backend.backend.report.domain.ReportStatus;
import backend.backend.report.dto.ReportRequest;
import backend.backend.report.dto.ReportStatusChangeRequest;
import backend.backend.report.repository.ReportRepository;
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

    public Long createReport(User currentUser, ReportRequest reportRequest) {
        User reporter = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        throwExceptionIfCurrentUserAlreadyReportedSameBoardOrComment(reporter, reportRequest);
        return reportRepository.save(reportRequest.toEntity(reporter)).getId();
    }

    private void throwExceptionIfCurrentUserAlreadyReportedSameBoardOrComment(User reporter, ReportRequest reportRequest) {
        reportRepository.findReportByReporterIdAndReportCategoryAndTargetId(reporter.getId(), reportRequest.reportCategory(), reportRequest.targetId())
                .ifPresent(present -> {
                    if (present.getReportCategory().equals(ReportCategory.BOARD)) {
                        throw new ReportException(ErrorCode.ALREADY_BOARD_REPORT_EXIST);
                    }

                    throw new ReportException(ErrorCode.ALREADY_COMMENT_REPORT_EXIST);
                });
    }

    @Transactional
    public void updateReportStatus(Long reportId, ReportStatusChangeRequest reportStatusChangeRequest) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REPORT_NOT_FOUND));
        report.updateStatus(reportStatusChangeRequest.reportStatus());
        updateUserGenerateContentReportCount(report, reportStatusChangeRequest);
    }

    private void updateUserGenerateContentReportCount(Report report, ReportStatusChangeRequest reportStatusChangeRequest) {
        if (report.getReportCategory().equals(ReportCategory.BOARD)) {
            Board board = boardRepository.findById(report.getTargetId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

            if (reportStatusChangeRequest.reportStatus().equals(ReportStatus.ACCEPT)) {
                board.addReportCount();
            }
        }

        if (report.getReportCategory().equals(ReportCategory.COMMENT)) {
            Comment comment = commentRepository.findById(report.getTargetId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

            if (reportStatusChangeRequest.reportStatus().equals(ReportStatus.ACCEPT)) {
                comment.addReportCount();
            }
        }
    }

    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
