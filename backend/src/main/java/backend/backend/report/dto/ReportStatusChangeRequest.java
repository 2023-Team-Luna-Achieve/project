package backend.backend.report.dto;

import backend.backend.report.domain.ReportStatus;
import jakarta.validation.constraints.NotNull;

public record ReportStatusChangeRequest(
        @NotNull
        ReportStatus reportStatus
) {
}