package backend.backend.common.event;

public record CommentCreateEvent(
        String senderName,
        String content,
        Long targetId,
        Long receiverId
) {
}
