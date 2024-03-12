package backend.backend.reservation.repository;

import backend.backend.reservation.dto.MeetingRoomReservationAvailTimeResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static backend.backend.reservation.entity.QReservation.reservation;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MeetingRoomReservationAvailTimeResponse> findReservationsByRoomIdAndReservedTime(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime) {
        return queryFactory.select(Projections.constructor(MeetingRoomReservationAvailTimeResponse.class,
                        reservation.reservationStartTime,
                        reservation.reservationEndTime
                        ))
                .from(reservation)
                .where(goeNowDateTimeLoeNextDateTime(meetingRoomId, startTime, endTime))
                .orderBy(reservation.reservationStartTime.asc())
                .fetch();
    }

    private BooleanExpression goeNowDateTimeLoeNextDateTime(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime) {
        return reservation.reservationStartTime.goe(startTime).and(reservation.reservationStartTime.loe(endTime)).and(reservation.meetingRoom.id.eq(meetingRoomId));
    }
}
