package backend.backend.common.event;

public record ReservationReminderEvent(
        String receiverName,
        Long targetId,
        Long receiverId
) {
}
