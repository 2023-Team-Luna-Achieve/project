package backend.backend.reservation.service;

import backend.backend.auth.service.EmailService;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.meetingroom.service.MeetingRoomService;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import backend.backend.user.dto.SignUpResponse;
import backend.backend.user.dto.UserDto;
import backend.backend.user.entity.User;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public ReservationResponse createReservation(ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        Reservation reservation = makeReservation(request);
        return convertToResponse(reservation);
    }

    @Transactional
    public Reservation makeReservation(ReservationRequest request) throws MessagingException, UnsupportedEncodingException {
        Long userId = (long) session.getAttribute("userId");
        User user = userService.findById(userId);
        MeetingRoom meetingRoom = meetingRoomService.findById(request.getMeetingRoomId())
                .orElse(new MeetingRoom());
        Reservation reservation = request.toEntity(user, meetingRoom);

        emailService.sendReservationEmail(user.getEmail(), meetingRoom.getName());
        return reservationRepository.save(reservation);
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


    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> responses = reservations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return responses;
    }

    public boolean cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation == null) {
            return false;
        }
        reservationRepository.delete(reservation);
        return true;
    }
}
