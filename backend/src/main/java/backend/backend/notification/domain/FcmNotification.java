package backend.backend.notification.domain;

import backend.backend.common.constant.FcmNotificationCategory;
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
    @Column(name = "fcm_notification_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FcmNotificationCategory fcmNotificationCategory;

    private String content;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private Long receiverId;

    private boolean isRead;

    @Builder
    public FcmNotification(String content, FcmNotificationCategory fcmNotificationCategory, Long targetId, Long receiverId, boolean isRead) {
        this.fcmNotificationCategory = fcmNotificationCategory;
        this.content = content;
        this.targetId = targetId;
        this.receiverId = receiverId;
        this.isRead = isRead;
    }

    public void read() {
        this.isRead = true;
    }
}
