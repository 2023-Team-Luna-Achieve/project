package backend.backend.reservation.service;

import backend.backend.auth.service.EmailService;
import backend.backend.common.exception.*;
import backend.backend.reservation.dto.MeetingRoomReservationAvailTimeResponse;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.repository.MeetingRoomRepository;
import backend.backend.reservation.dto.ReservationCountResponse;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomService;
    private final UserRepository userRepository;
    private final EmailService emailService;


    @Transactional
    public Long makeReservation(User user, ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        MeetingRoom meetingRoom = meetingRoomService.findById(request.meetingRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLUB_ROOM_NOT_FOUND));

        User reservUser = userRepository.findUserByEmail(user.getEmail());

        checkUserHasReservation(user);
        reservationTimeValidator(request);

        Reservation reservation = request.toEntity(user, meetingRoom);
        reservUser.addReservationCount();

        try {
            emailService.sendReservationEmail(user.getEmail(), meetingRoom.getName());
        } catch (IllegalArgumentException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return reservationRepository.save(reservation).getId();
    }

    private void reservationTimeValidator(ReservationRequest request) {
        if (isReservationStartTimeFormatOneNotOneSec(request.reservationStartTime())) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME_REQUEST);
        }

        if (isTimeSlotAlreadyReserved(request.meetingRoomId(), request.reservationStartTime())) {
            throw new InvalidReservationTimeException(ErrorCode.ALREADY_RESERVED_TIME);
        }

        if (request.reservationStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME_REQUEST);
        }

        if (request.reservationStartTime().isAfter(request.reservationEndTime())) {
            throw new InvalidReservationTimeException(ErrorCode.INVALID_RESERVATION_TIME_REQUEST);
        }
    }

    private void checkUserHasReservation(User user) {
        if (reservationRepository.existsReservationByUserId(user.getId())) {
            throw new AlreadyReservationExistException(ErrorCode.ALREADY_RESERVATION_EXIST);
        }
    }

    private boolean isTimeSlotAlreadyReserved(Long meetingRoomId, LocalDateTime startTime) {
        return reservationRepository.existsReservationByMeetingRoomIdAndReservationStartTime(meetingRoomId, startTime);
    }

    private boolean isReservationStartTimeFormatOneNotOneSec(LocalDateTime startTime) {
        return startTime.getMinute() > 0 || startTime.getSecond() != 1;
    }

    public ReservationResponse convertToResponse(Reservation reservation) {
        String startTimeAlert = responseTimeMaker(reservation.getReservationStartTime());
        String endTimeAlert = responseTimeMaker(reservation.getReservationEndTime());
        return ReservationResponse.from(reservation, startTimeAlert, endTimeAlert);
    }

    private String responseTimeMaker(LocalDateTime reservationTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
        return reservationTime.format(formatter);
    }

    public List<ReservationResponse> getReservationsByMeetingRoomId(Long roomId) {
        List<Reservation> roomReservations = reservationRepository.findAllByMeetingRoomId(roomId);

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

    public ReservationResponse getReservationsByUserId(Long userId) {
        return convertToResponse(reservationRepository.findReservationsByUserId(userId)
                .orElseGet(ReservationService::defaultReservation));
    }

    public static Reservation defaultReservation() {
        return new Reservation(0L, null, LocalDateTime.now(), LocalDateTime.now(), 0, null);
    }

    public ReservationCountResponse getReservationsCountByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ReservationNotExistException(ErrorCode.RESERVATION_NOT_FOUND));
        return ReservationCountResponse.of(user.getReservationCount());
    }


    public List<MeetingRoomReservationAvailTimeResponse> getReserveAvailTimes(Long meetingRoomId, String dateTime) {
        String todayTimeFormatter = timeFormatterToOclockSharp(dateTime);
        LocalDateTime previewReservationTimeStart = LocalDateTime.parse(todayTimeFormatter);
        LocalDateTime previewReservationTimeEnd = tomorrowTimeFormatter(todayTimeFormatter);
        return reservationRepository.findReservationsByRoomIdAndReservedTime(meetingRoomId, previewReservationTimeStart, previewReservationTimeEnd);
    }

    private LocalDateTime tomorrowTimeFormatter(String time) {
        return LocalDateTime.parse(time).plusDays(2);
    }

    private String timeFormatterToOclockSharp(String dateTime) {
        String timeSubstring = dateTime.substring(11);
        String dateSubstring = dateTime.substring(0, 11);

        for (int i = 0; i < timeSubstring.length(); i++) {
            char c = timeSubstring.charAt(i);
            if (c != '0' && c != ':' && c != '.') {
                timeSubstring = timeSubstring.replace(String.valueOf(timeSubstring.charAt(i)), "0");
            }
        }
        return dateSubstring + timeSubstring;
    }
}
