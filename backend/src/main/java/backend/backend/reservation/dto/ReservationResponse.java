package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import lombok.Getter;
import lombok.Setter;

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
