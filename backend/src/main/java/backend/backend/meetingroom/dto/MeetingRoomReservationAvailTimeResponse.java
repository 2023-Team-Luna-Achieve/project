package backend.backend.meetingroom.dto;

import backend.backend.reservation.entity.Reservation;

public record MeetingRoomReservationAvailTimeResponse(
        String reserved
) {
    public static MeetingRoomReservationAvailTimeResponse from(Reservation reservation) {
        return new MeetingRoomReservationAvailTimeResponse(
                String.valueOf(reservation.getReservationStartTime()));
    }
}
