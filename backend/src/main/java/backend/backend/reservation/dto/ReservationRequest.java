package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.entity.Reservation;
import backend.backend.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private int members;
    private Long meetingRoomId;

    public Reservation toEntity(User user, MeetingRoom meetingRoom) {
        return Reservation.builder()
                .user(user)
                .reservationStartTime(reservationStartTime)
                .reservationEndTime(reservationEndTime)
                .members(members)
                .meetingRoom(meetingRoom)
                .created_at(LocalDateTime.now())
                .build();
    }
}
