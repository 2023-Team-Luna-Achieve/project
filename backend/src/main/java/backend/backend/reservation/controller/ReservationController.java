package backend.backend.reservation.controller;

import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final HttpSession httpSession;

    //예약 생성
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        ReservationResponse response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //예약 내역 조회
    @GetMapping("/check")
    public ResponseEntity<List<ReservationResponse>> getReservationsByUserId() {
        Long userId = (long) httpSession.getAttribute("userId");
        List<ReservationResponse> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    //전체 예약 내역 조회
    @GetMapping("/all")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> allReservations = reservationService.getAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    //예약 취소
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        boolean success = reservationService.cancelReservation(reservationId);

        if (success) {
            return ResponseEntity.ok("예약이 취소되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약을 찾을 수 없습니다.");
        }
    }
}
