package backend.backend.reservation.repository;

import backend.backend.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByMeetingRoomIdAndReservationStartTimeBetweenOrReservationEndTimeBetween(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime startTime1, LocalDateTime endTime1);
}
