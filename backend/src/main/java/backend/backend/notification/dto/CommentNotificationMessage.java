package backend.backend.notification.dto;

import backend.backend.notification.domain.FcmNotification;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CommentNotificationMessage(
        @JsonProperty("validate_only")
        boolean validateOnly,
        Message message
) {
    public static CommentNotificationMessage of(Boolean validateOnly, Message message) {
        return new CommentNotificationMessage(validateOnly, message);
    }

    public record Notification(
            String title,
            String body
//            String image
    ) {

        public static Notification of(String title, String body/*String image*/) {
            return new Notification(title, body /*image*/);
        }
    }

    public record Message(
            String token,
            Notification notification,
            Data data
    ) {

        public static Message of(String token, Notification notification, Data data) {
            return new Message(token, notification, data);
        }
    }

    public record Data(
            String targetId,
            String receiverId,
            String content,
            LocalDateTime createdAt
    ) {
        public static Data from(FcmNotification fcmNotification) {
            return new Data(
                    fcmNotification.getTargetId().toString(),
                    fcmNotification.getReceiverId().toString(),
                    fcmNotification.getContent(),
                    fcmNotification.getCreatedAt()
            );
        }
    }
}