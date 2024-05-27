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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportContent reportContent;

    private boolean isBlockUser;

    private String targetEntity;

    @JoinColumn(name = "reporter_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    @JoinColumn(name = "reported_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User reportedUser;

    @Builder
    public Report(ReportContent reportContent, boolean isBlockUser, User reportedUser, User reporter) {
        this.reportContent = reportContent;
        this.isBlockUser = isBlockUser;
        this.reporter = reporter;
        this.reportedUser = reportedUser;
    }

    public void changeStatusToBlockUser() {
        this.isBlockUser = true;
    }
}
