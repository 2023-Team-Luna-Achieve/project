package backend.backend.notification.dto;

import backend.backend.common.dto.Identifiable;
import backend.backend.notification.domain.FcmNotification;

public record FcmNotificationResponse(
        Long notificationId,
        String content,
        boolean isRead
) implements Identifiable {
    @Override
    public Long getId() {
        return notificationId;
    }
        public static FcmNotificationResponse from(FcmNotification fcmNotification){
        return new FcmNotificationResponse(
                fcmNotification.getId(),
                fcmNotification.getContent(),
                fcmNotification.isRead()
        );
    }
}
