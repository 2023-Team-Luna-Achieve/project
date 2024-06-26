package backend.backend.report.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.report.dto.ReportRequest;
import backend.backend.report.dto.ReportStatusChangeRequest;
import backend.backend.report.service.ReportService;
import backend.backend.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> report(@Valid @CurrentUser User currentUser, @RequestBody ReportRequest reportRequest) {
        Long reportId = reportService.createReport(currentUser, reportRequest);
        return ResponseEntity.created(URI.create("/api/reports/" + reportId)).build();
    }

    // admin
    @PatchMapping("/{reportId}")
    public ResponseEntity<Void> updateReportStatus(@PathVariable Long reportId,
                                                   @RequestBody @Valid ReportStatusChangeRequest reportStatusChangeRequest) {
        reportService.updateReportStatus(reportId, reportStatusChangeRequest);
        return ResponseEntity.ok().build();
    }
}
