package backend.backend.reservation.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.auth.jwt.CustomUserDetails;
import backend.backend.auth.jwt.UserAdapter;
import backend.backend.exception.ErrorCode;
import backend.backend.exception.NotFoundException;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.service.ReservationService;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final UserService userService;
    private final ReservationService reservationService;
    //예약 생성
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@CurrentUser User currentUser, @RequestBody ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        ReservationResponse response = reservationService.createReservation(currentUser, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //예약 내역 조회
    @GetMapping("/check")
    public ResponseEntity<List<ReservationResponse>> getReservationsByUserId(@CurrentUser User currentUser) {
        List<ReservationResponse> reservations = reservationService.getReservationsByUserId(currentUser.getId());
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
