package backend.backend.notification.dto;

import backend.backend.notification.domain.FcmNotification;

public record FcmNotificationResponse (
        Long notificationId,
        String content,
        boolean isRead
) {
    public static FcmNotificationResponse from(FcmNotification fcmNotification) {
        return new FcmNotificationResponse(
                fcmNotification.getId(),
                fcmNotification.getContent(),
                fcmNotification.isRead()
        );
    }
}
