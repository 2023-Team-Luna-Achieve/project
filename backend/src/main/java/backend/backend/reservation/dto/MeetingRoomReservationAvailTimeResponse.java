package backend.backend.reservation.dto;

import java.time.LocalDateTime;

public record MeetingRoomReservationAvailTimeResponse(
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime
) {
}
