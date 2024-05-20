package backend.backend.reservation.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.reservation.dto.MeetingRoomReservationAvailTimeResponse;
import backend.backend.reservation.dto.ReservationCountResponse;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.service.ReservationService;
import backend.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

@Tag(name = "Reservation API", description = "예약")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    //예약 생성
    @Operation(tags = "예약 생성 API", description = "예약 생성을 진행한다")
    @PostMapping
    public ResponseEntity<Void> createReservation(@CurrentUser User currentUser, @RequestBody ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        Long reservationId = reservationService.makeReservation(currentUser, request);
        return ResponseEntity.created(URI.create("/api/reservation/" + reservationId)).build();
    }

    @Operation(tags = "유저기준 예약 조회 API", description = "로그인된 유저 기준 전체 예약 조회를 진행한다")
    @GetMapping("/my")
    public ResponseEntity<ReservationResponse> getReservationsByUserId(@CurrentUser User currentUser) {
        return ResponseEntity.ok().body(reservationService.getReservationsByUserId(currentUser.getId()));
    }

    @GetMapping("/reservation-count")
    public ResponseEntity<ReservationCountResponse> getReservationCount(@CurrentUser User currentUser) {
        return ResponseEntity.ok().body(reservationService.getReservationsCountByUserId(currentUser.getId()));
    }

    @Operation(tags = "미팅룸 아이디 기준 예약 조회 API", description = "미팅룸 기준 전체 예약 조회를 진행한다")
    @GetMapping("/meetingroom/{roomId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok().body(reservationService.getReservationsByMeetingRoomId(roomId));
    }

    //전체 예약 내역 조회
    @Operation(tags = "전체 예약 조회 API", description = "존재하는 전체 예약을 조회를 진행한다")
    @GetMapping("/all")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> allReservations = reservationService.getAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    //예약 취소
    @Operation(tags = "예약 취소 API", description = "예약 취소를 진행한다")
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId,
                                                  @CurrentUser User user) {
        reservationService.cancelReservation(user, reservationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "각 동아리방 별 예약 가능 시간 조회 API", description = "각 방별 예약 가능 시간을 전체조회 한다.")
    @GetMapping("/avail/{meetingRoomId}")
    public ResponseEntity<List<MeetingRoomReservationAvailTimeResponse>> getAvailReservationTime(@PathVariable Long meetingRoomId,
                                                                                                 @RequestParam(required = false) String dateTime) {
        return ResponseEntity.ok().body(reservationService.getReserveAvailTimes(meetingRoomId, dateTime));
    }


    @Operation(summary = "각 동아리방 별 예약 가능 시간 조회 API", description = "각 방별 예약 가능 시간을 전체조회 한다.")
    @GetMapping("/reservationReminder")
    public ResponseEntity<Void> d() {
        reservationService.reservationReminderNotification();
        return ResponseEntity.ok().build();
    }
}
