package backend.backend.reservation.service;

import backend.backend.auth.service.EmailService;
import backend.backend.common.exception.AuthException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.InvalidReservationTimeException;
import backend.backend.common.exception.NotFoundException;
import backend.backend.reservation.dto.MeetingRoomReservationAvailTimeResponse;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.repository.MeetingRoomRepository;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomService;
    private final EmailService emailService;


    @Transactional
    public Long makeReservation(User user, ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        MeetingRoom meetingRoom = meetingRoomService.findById(request.getMeetingRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLUB_ROOM_NOT_FOUND));
        // 이미 예약된 시간인지 확인
        reservationTimeValidator(request);

        Reservation reservation = request.toEntity(user, meetingRoom);

        emailService.sendReservationEmail(user.getEmail(), meetingRoom.getName());
        return reservationRepository.save(reservation).getId();
    }

    private void reservationTimeValidator(ReservationRequest request) {
        if (isTimeSlotAlreadyReserved(request.getMeetingRoomId(), request.getReservationStartTime())) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME_REQUEST);
        }

        if (request.getReservationStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME_REQUEST);
        }

        if (request.getReservationStartTime().isAfter(request.getReservationEndTime())) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME_REQUEST);
        }
    }

    private boolean isTimeSlotAlreadyReserved(Long meetingRoomId, LocalDateTime startTime) {
        return reservationRepository.existsReservationByMeetingRoomIdAndAndReservationStartTime(meetingRoomId, startTime);
    }

    public ReservationResponse convertToResponse(Reservation reservation) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
        return reservationStartTime.format(formatter);
    }

    private String endTimeMaker(LocalDateTime reservationEndTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
        return reservationEndTime.format(formatter);
    }

    public List<ReservationResponse> getReservationsByUserId(Long userId) {
        List<Reservation> userReservations = reservationRepository.findByUserId(userId);

        if (userReservations.isEmpty()) {
            return Collections.emptyList();
        }

        return userReservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservationsByMeetingRoomId(Long roomId) {
        List<Reservation> roomReservations = reservationRepository.findByMeetingRoomId(roomId);

        if (roomReservations.isEmpty()) {
            return Collections.emptyList();
        }

        return roomReservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    public List<ReservationResponse> getAllReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return allReservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public void cancelReservation(User user, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESERVATION_NOT_FOUND));

        if (user.hasAuthority(reservation.getUser().getId())) {
            throw new AuthException(ErrorCode.FORBIDDEN);
        }

        reservationRepository.delete(reservation);
    }

    public List<MeetingRoomReservationAvailTimeResponse> getReserveAvailTimes(Long meetingRoomId) {
        String todayTimeFormatter = timeFormatter();
        LocalDateTime previewReservationTimeStart = LocalDateTime.parse(todayTimeFormatter);
        LocalDateTime previewReservationTimeEnd = tomorrowTimeFormatter(todayTimeFormatter);
        return reservationRepository.findReservationsByRoomIdAndReservedTime(meetingRoomId, previewReservationTimeStart, previewReservationTimeEnd);
    }

    private LocalDateTime tomorrowTimeFormatter(String time) {
        return LocalDateTime.parse(time).plusDays(2);
    }

    private String timeFormatter() {
        String now = String.valueOf(LocalDateTime.now());
        String timeSubstring = now.substring(11);
        String dateSubstring = now.substring(0, 11);

        for (int i = 0; i < timeSubstring.length(); i++) {
            char c = timeSubstring.charAt(i);
            if (c != '0' && c != ':' && c != '.') {
                timeSubstring = timeSubstring.replace(String.valueOf(timeSubstring.charAt(i)), "0");
            }
        }
        return dateSubstring + timeSubstring;
    }
}
