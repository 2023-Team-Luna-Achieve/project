package backend.backend.reservation.dto;

import java.time.LocalDateTime;

public record ReservationCountResponse(
    int reservationCount
) {
    public static ReservationCountResponse of(int reservationCount) {
        return new ReservationCountResponse(reservationCount);
    }
}
