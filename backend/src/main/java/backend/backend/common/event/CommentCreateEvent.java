package backend.backend.common.event;

import backend.backend.common.constant.FcmNotificationCategory;

public record CommentCreateEvent(
        FcmNotificationCategory fcmNotificationCategory,
        String senderName,
        String content,
        Long targetId,
        Long receiverId
) {

}
