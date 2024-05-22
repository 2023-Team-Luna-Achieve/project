package backend.backend.notification.dto;

import backend.backend.common.constant.FcmNotificationCategory;
import backend.backend.common.dto.Identifiable;

public record FcmNotificationResponse(
        FcmNotificationCategory fcmNotificationCategory,
        Long targetId,
        Long notificationId,
        String content,
        boolean isRead
) implements Identifiable {
    @Override
    public Long getId() {
        return notificationId;
    }
//        public static FcmNotificationResponse from(FcmNotification fcmNotification){
//        return new FcmNotificationResponse(
//                fcmNotification.getFcmNotificationCategory(),
//                fcmNotification.getId(),
//                fcmNotification.getTargetId(),
//                fcmNotification.getContent(),
//                fcmNotification.isRead()
//        );
//    }
}
