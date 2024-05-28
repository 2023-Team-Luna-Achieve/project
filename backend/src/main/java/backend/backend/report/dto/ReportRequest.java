package backend.backend.report.dto;

import backend.backend.report.domain.Report;
import backend.backend.report.domain.ReportCategory;
import backend.backend.report.domain.ReportContent;
import backend.backend.user.entity.User;

public record ReportRequest(
        Long targetId,
        ReportCategory reportCategory,
        ReportContent reportContent
) {

    public Report toEntity(User user) {
        return Report.builder()
                .targetId(targetId)
                .reporter(user)
                .reportContent(reportContent)
                .reportCategory(reportCategory)
                .status("pending")
                .build();
    }
}
