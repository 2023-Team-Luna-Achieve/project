package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.entity.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public record ReservationResponse(
        Long id,
        String startTime,
        String endTime,
        int members,
        MeetingRoom meetingRoom
) {

    public static ReservationResponse empty() {
        return new ReservationResponse(0L, null, null, 0, null);
    }

    public static ReservationResponse from(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
        return new ReservationResponse(
                reservation.getId(),
                reservation.getReservationStartTime().format(formatter),
                reservation.getReservationEndTime().format(formatter),
                reservation.getMembers(),
                reservation.getMeetingRoom()
        );
    }
}
