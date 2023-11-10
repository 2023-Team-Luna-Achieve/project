package backend.backend.reservation.controller;

import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    //예약 생성
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
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
        String startTimeAlert = startTimeMaker(reservation.getReservationStartTime());
        String endTimeAlert = endTimeMaker(reservation.getReservationEndTime());
        return new ReservationResponse(
                reservation.getId(),
                startTimeAlert,
                endTimeAlert,
                reservation.getMembers(),
                reservation.getMeetingRoom()
        );
    }

    private String startTimeMaker(LocalDateTime reservationStartTime) {
        int year = reservationStartTime.getYear();
        int month = reservationStartTime.getMonthValue();
        int day = reservationStartTime.getDayOfMonth();

        int hour = reservationStartTime.getHour();
        int minute = reservationStartTime.getMinute();
        int second = reservationStartTime.getSecond();
        return year + "년 " + month + "월 " + day + "일 " +  hour + ":" + minute + ":"+ second;
    }

    private String endTimeMaker(LocalDateTime reservationEndTime) {
        int year = reservationEndTime.getYear();
        int month = reservationEndTime.getMonthValue();
        int day = reservationEndTime.getDayOfMonth();

        int hour = reservationEndTime.getHour();
        int minute = reservationEndTime.getMinute();
        int second = reservationEndTime.getSecond();
        return year + "년 " + month + "월 " + day + "일 " +  hour + ":" + minute + ":"+ second;
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
