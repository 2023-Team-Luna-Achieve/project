package backend.backend.reservation.service;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    private ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation makeReservation(ReservationRequest request) {

        Reservation reservation = new Reservation();
        reservation.setYear(request.getYear());
        reservation.setMonth(request.getMonth());
        reservation.setDate(request.getDate());
        reservation.setReservationTime(request.getReservationTime());
        reservation.setMembers(request.getMembers());
        reservation.setMeetingRoom(request.getMeetingRoom());

        return reservationRepository.save(reservation);

    }

    public List<Reservation> getAllReservations() {

        return reservationRepository.findAll();
    }
}
