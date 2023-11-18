package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse {

    private Long id;
    private String startTime;
    private String endTime;
    private int members;
    private MeetingRoom meetingRoom;

    public ReservationResponse(Long id, String startTime, String endTime, int members, MeetingRoom meetingRoom) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.members = members;
        this.meetingRoom = meetingRoom;
    }
}
