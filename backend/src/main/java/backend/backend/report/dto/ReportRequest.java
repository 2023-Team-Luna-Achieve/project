package backend.backend.report.dto;

import backend.backend.report.domain.Report;
import backend.backend.report.domain.ReportedUserGeneratedCategory;
import backend.backend.report.domain.ReportContent;
import backend.backend.user.entity.User;
import jakarta.validation.constraints.NotNull;

public record ReportRequest(
        Long targetId,
        @NotNull
        ReportedUserGeneratedCategory reportedUserGeneratedCategory,
        ReportContent reportContent
) {

    public Report toEntity(User user) {
        return Report.builder()
                .targetId(targetId)
                .reporter(user)
                .reportContent(reportContent)
                .reportedUserGeneratedCategory(reportedUserGeneratedCategory)
                .build();
    }
}
