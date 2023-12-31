package backend.backend.reservation.service;

import backend.backend.auth.service.EmailService;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.service.MeetingRoomService;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import backend.backend.user.entity.User;
import backend.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final HttpSession session;
    private final ReservationRepository reservationRepository;
    private final MeetingRoomService meetingRoomService;
    private final UserService userService;
    private final EmailService emailService;

    public ReservationResponse createReservation(User user, ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        Reservation reservation = makeReservation(user, request);
        return convertToResponse(reservation);
    }

    @Transactional
    public Reservation makeReservation(User user, ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        MeetingRoom meetingRoom = meetingRoomService.findById(request.getMeetingRoomId())
                .orElse(new MeetingRoom());

        // 이미 예약된 시간인지 확인
        if (isTimeSlotAlreadyReserved(request.getMeetingRoomId(), request.getReservationStartTime(), request.getReservationEndTime())) {
            throw new RuntimeException("이미 예약된 시간입니다.");
        }
        Reservation reservation = request.toEntity(user, meetingRoom);

        emailService.sendReservationEmail(user.getEmail(), meetingRoom.getName());
        return reservationRepository.save(reservation);
    }

    private boolean isTimeSlotAlreadyReserved(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 주어진 회의실과 시간 범위에 대해 이미 예약된 예약이 있는지 확인
        List<Reservation> existingReservations = reservationRepository.findByMeetingRoomIdAndReservationStartTimeBetweenOrReservationEndTimeBetween(
                meetingRoomId, startTime, endTime, startTime, endTime
        );
        return !existingReservations.isEmpty();
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

        if(userReservations.isEmpty()) {
            return Collections.emptyList();
        }

        return userReservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return allReservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public boolean cancelReservation(Long reservationId) {
        Long userId = (long) session.getAttribute("userId");
        User user = userService.findById(userId);

        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation == null || !reservation.getUser().getId().equals(userId)) {
            return false;
        }
        reservationRepository.delete(reservation);
        return true;
    }
}
