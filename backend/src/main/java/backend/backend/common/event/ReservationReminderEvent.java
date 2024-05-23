package backend.backend.common.event;

import backend.backend.common.constant.FcmNotificationCategory;

public record ReservationReminderEvent (
        FcmNotificationCategory fcmNotificationCategory,
        String receiverName,
        Long targetId,
        Long receiverId
) {
}
