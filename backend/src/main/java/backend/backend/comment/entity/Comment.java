package backend.backend.comment.entity;

import backend.backend.common.domain.BaseEntity;
import backend.backend.board.entity.Board;
import backend.backend.common.domain.UserGeneratedContent;
import backend.backend.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity implements UserGeneratedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String context;

    @Column(name = "report_count")
    @ColumnDefault("0")
    private int reportCount;

    public Comment update(String context) {
        this.context = context;
        return this;
    }

    @Override
    public void addReportCount() {
        this.reportCount ++;
    }

    @Override
    public void minusReportCount() {
        this.reportCount --;
    }
}