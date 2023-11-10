package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {

    private int year;
    private int month;
    private int date;
    private LocalDateTime reservationTime;
    private int members;
    private MeetingRoom meetingRoom;

    public ReservationRequest(int year, int month, int date, LocalDateTime reservationTime, int members, MeetingRoom meetingRoom) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.reservationTime = reservationTime;
        this.members = members;
        this.meetingRoom = meetingRoom;
    }
}