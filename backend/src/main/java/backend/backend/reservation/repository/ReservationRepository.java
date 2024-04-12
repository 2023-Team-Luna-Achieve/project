package backend.backend.reservation.repository;

import backend.backend.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom{
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findAllByMeetingRoomId(Long meetingRoomId);
    List<Reservation> findByMeetingRoomId(Long roomId);
    boolean existsReservationByMeetingRoomIdAndAndReservationStartTime(Long meetingRoomId, LocalDateTime startTime);
    List<Reservation> findReservationsByMeetingRoomIdAndReservationStartTime(Long meetingRoomId, LocalDateTime dateTime);
}
