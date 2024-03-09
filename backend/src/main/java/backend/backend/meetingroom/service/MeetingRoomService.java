package backend.backend.meetingroom.service;

import backend.backend.meetingroom.dto.MeetingRoomReservationAvailTimeResponse;
import backend.backend.meetingroom.dto.MeetingRoomResponse;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.repository.MeetingRoomRepository;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    public void saveMeetingRoom(MeetingRoom meetingRoom) {
        meetingRoomRepository.save(meetingRoom);
    }


    public List<MeetingRoomResponse> getMeetingRooms() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        return meetingRooms.stream()
                .map(MeetingRoomResponse::from)
                .toList();
    }

    public List<MeetingRoomReservationAvailTimeResponse> getReserveAvailTimes() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                reservation.getReservationStartTime();
            }
        }
        return reservations.stream()
                .map(MeetingRoomReservationAvailTimeResponse::from)
                .toList();
    }
}
