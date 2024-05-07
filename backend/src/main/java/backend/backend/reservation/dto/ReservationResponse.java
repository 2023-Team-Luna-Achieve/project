package backend.backend.reservation.dto;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.entity.Reservation;


public record ReservationResponse(
        Long id,
        String startTime,
        String endTime,
        int members,
        MeetingRoom meetingRoom
) {
    public static ReservationResponse from(Reservation reservation, String formattedStartTime, String formattedEndTime) {
        return new ReservationResponse(
                reservation.getId(),
                formattedStartTime,
                formattedEndTime,
                reservation.getMembers(),
                reservation.getMeetingRoom()
        );
    }

//    public ReservationResponse(Long id, String startTime, String endTime, int members, MeetingRoom meetingRoom) {
//        this.id = id;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.members = members;
//        this.meetingRoom = meetingRoom;
//    }

//    public ReservationResponse(Long id, String startTime, String endTime, int members) {
//        this.id = id;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.members = members;
//    }
//
//    public ReservationResponse() {
//
//    }
}
