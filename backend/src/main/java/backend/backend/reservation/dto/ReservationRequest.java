package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.entity.Reservation;
import backend.backend.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

public record ReservationRequest(
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime,
        int members,
        Long meetingRoomId
) {
    public Reservation toEntity(User user, MeetingRoom meetingRoom) {
        return Reservation.builder()
                .user(user)
                .reservationStartTime(reservationStartTime)
                .reservationEndTime(reservationEndTime)
                .members(members)
                .meetingRoom(meetingRoom)
                .build();
    }
}
