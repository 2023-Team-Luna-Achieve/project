package backend.backend.reservation.controller;

import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //예약 생성
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.makeReservation(request);
        ReservationResponse response = convertToResponse(reservation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //예약 정보 가져오기
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {

        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationResponse> responses = reservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getYear(),
                reservation.getMonth(),
                reservation.getDate(),
                reservation.getReservationTime(),
                reservation.getMembers(),
                reservation.getMeetingRoom()
        );
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
