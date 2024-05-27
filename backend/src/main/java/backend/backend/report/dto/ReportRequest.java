package backend.backend.report.dto;

import backend.backend.report.domain.Report;
import backend.backend.report.domain.ReportContent;
import backend.backend.user.entity.User;
import jakarta.validation.constraints.NotNull;

public record ReportRequest(
        String reportedUserEmail,
        @NotNull
        Boolean isBlockUser,
        ReportContent reportContent
) {
    public Report toEntity(User reporter, User reportedUser) {
        return Report.builder()
                .reportContent(reportContent)
                .isBlockUser(isBlockUser)
                .reporter(reporter)
                .reportedUser(reportedUser)
                .build();
    }
}
