package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.entity.Reservation;
import backend.backend.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {

    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private int members;
    private Long meetingRoomId;

    public ReservationRequest(LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, int members, Long meetingRoomId) {

        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.members = members;
        this.meetingRoomId = meetingRoomId;
    }

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
