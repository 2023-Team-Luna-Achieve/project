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
public class Block extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Long id;

    @JoinColumn(name = "blocker_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User blocker;

    @JoinColumn(name = "blocked_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User blockedUser;

    @Builder
    public Block(User blocker, User blockedUser) {
        this.blocker = blocker;
        this.blockedUser = blockedUser;
    }
}
