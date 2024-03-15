package backend.backend.reservation.repository;

import backend.backend.reservation.dto.MeetingRoomReservationAvailTimeResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepositoryCustom {
    List<MeetingRoomReservationAvailTimeResponse> findReservationsByRoomIdAndReservedTime(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime);
}
