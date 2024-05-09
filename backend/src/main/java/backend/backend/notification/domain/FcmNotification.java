package backend.backend.notification.domain;

import backend.backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmNotification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private Long receiverId;

    private boolean isRead;

    @Builder
    public FcmNotification(String content, Long targetId, Long receiverId) {
        this.content = content;
        this.targetId = targetId;
        this.receiverId = receiverId;
    }

    public FcmNotification(String content) {
        this.content = content;
    }
}
