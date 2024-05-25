package backend.backend.report.service;

import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
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
        User reporter = findUser(currentUser.getId());
        User reportedUser = findUser(reportRequest.reportedUserId());
        return reportRepository.save(reportRequest.toEntity(reporter, reportedUser)).getId();
    }

    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }


    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
