package backend.backend.reservation.repository;

import backend.backend.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom{
    boolean existsReservationByUserId(Long userId);
    Optional<Reservation> findReservationByUserId(Long userId);
    List<Reservation> findAllByMeetingRoomId(Long meetingRoomId);
    boolean existsReservationByMeetingRoomIdAndReservationStartTime(Long meetingRoomId, LocalDateTime startTime);
    List<Reservation> findReservationByReservationStartTime(LocalDateTime startTime);
    void deleteReservationsByReservationStartTimeBefore(LocalDateTime endTime);
}
