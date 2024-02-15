package backend.backend.reservation.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.service.ReservationService;
import backend.backend.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

@Api(tags = "Reservation API", description = "예약")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    //예약 생성
    @ApiOperation(value = "예약 생성 API", notes = "예약 생성을 진행한다")
    @PostMapping
    public ResponseEntity<Void> createReservation(@CurrentUser User currentUser, @RequestBody ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        Long reservationId = reservationService.createReservation(currentUser, request);
        return ResponseEntity.created(URI.create("/api/reservation/" + reservationId)).build();
    }

    @ApiOperation(value = "유저기준 예약 조회 API", notes = "로그인된 유저 기준 전체 예약 조회를 진행한다")
    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> getReservationsByUserId(@CurrentUser User currentUser) {
        List<ReservationResponse> reservations = reservationService.getReservationsByUserId(currentUser.getId());
        return ResponseEntity.ok(reservations);
    }

    //예약 내역 조회
    @ApiOperation(value = "유저기준 예약 조회 API", notes = "로그인된 유저 기준 전체 예약 조회를 진행한다")
    @GetMapping("/meetingroom/{roomId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRoomId(@PathVariable Long roomId) {
        List<ReservationResponse> reservations = reservationService.getReservationsByMeetingRoomId(roomId);
        return ResponseEntity.ok(reservations);
    }

    //전체 예약 내역 조회
    @ApiOperation(value = "전체 예약 조회 API", notes = "존재하는 전체 예약을 조회를 진행한다")
    @GetMapping("/all")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> allReservations = reservationService.getAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    //예약 취소
    @ApiOperation(value = "예약 취소 API", notes = "예약 취소를 진행한다")
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId,
                                                  @CurrentUser User user) {
        reservationService.cancelReservation(user, reservationId);
        return ResponseEntity.noContent().build();
    }
}
