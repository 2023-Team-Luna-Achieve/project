package backend.backend.report.domain;

import backend.backend.common.domain.BaseEntity;
import backend.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @JoinColumn(name = "reporter_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    @JoinColumn(name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private ReportedUserGeneratedCategory reportedUserGenerateContentCategory;

    @Enumerated(EnumType.STRING)
    private ReportContent reportContent;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @PrePersist
    public void defaultStatusSetting() {
        if (this.status == null) {
            this.status = ReportStatus.PENDING;
        }
    }

    @Builder
    public Report(Long targetId, User reporter, ReportedUserGeneratedCategory reportedUserGenerateContentCategory, ReportContent reportContent) {
        this.targetId = targetId;
        this.reporter = reporter;
        this.reportedUserGenerateContentCategory = reportedUserGenerateContentCategory;
        this.reportContent = reportContent;
    }

    public void updateStatus(ReportStatus reportStatus) {
        this.status = reportStatus;
    }
}
