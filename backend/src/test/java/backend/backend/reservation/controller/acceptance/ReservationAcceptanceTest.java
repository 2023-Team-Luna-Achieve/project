package backend.backend.reservation.controller.acceptance;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.common.acceptance.AcceptanceTest;
import backend.backend.common.acceptance.step.AcceptanceStep;
import backend.backend.reservation.controller.acceptance.step.ReservationAcceptanceStep;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.controller.step.UserAcceptanceStep;
import backend.backend.user.dto.AuthResponse;
import backend.backend.user.dto.SignInRequest;
import backend.backend.user.dto.SignUpRequest;
import backend.backend.user.entity.Affiliation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DisplayName("Reservation 인수 테스트")
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ReservationAcceptanceTest extends AcceptanceTest {

    @Autowired
    RedisUtil redisUtil;

    private static SignUpRequest signUpRequest;

    private static SignInRequest signInRequest;

    private static AuthResponse authResponse;


    @BeforeEach
    @Override
    public void setUp() {
        LocalDateTime startTime = LocalDateTime.parse("2023-12-06T15:49:52.978Z", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endTime = LocalDateTime.parse("2023-12-06T17:49:52.978Z", DateTimeFormatter.ISO_DATE_TIME);

        UserAcceptanceStep.requestEmailVerifiedCodeSend(new EmailRequest("jaeyoon321@naver"));
        String data = redisUtil.getData("jaeyoon321@naver");
        UserAcceptanceStep.requestEmailVerified(new VerificationRequest("jaeyoon321@naver", data));

        // 회원가입
        signUpRequest = new SignUpRequest("유재윤", "jaeyoon321@naver", "1105", Affiliation.Techeer);
        UserAcceptanceStep.requestSignUp(signUpRequest);

        // 로그인
        signInRequest = new SignInRequest("jaeyoon321@naver", "1105");
        authResponse = AuthAcceptanceStep.createTokenByLogin(signInRequest);

        ReservationRequest reservationRequest = new ReservationRequest(startTime, endTime, 5, 1L);

        ReservationAcceptanceStep.requestMakeReservation(reservationRequest, authResponse);
    }


    @DisplayName("동아리방을 예약한다")
    @Order(1)
    @Test
    void requestMakeReservation() {
        AuthResponse authResponse = AuthAcceptanceStep.createTokenByLogin(signInRequest);
        LocalDateTime startTime = LocalDateTime.parse("2024-02-09T15:49:52.978Z", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endTime = LocalDateTime.parse("2024-02-10T17:49:52.978Z", DateTimeFormatter.ISO_DATE_TIME);
        ReservationRequest reservationRequest = new ReservationRequest(startTime, endTime, 5, 1L);

        ExtractableResponse<Response> makeReservationResponse = ReservationAcceptanceStep.requestMakeReservation(reservationRequest, authResponse);
        AcceptanceStep.assertThatStatusIsCreated(makeReservationResponse);
    }

    @DisplayName("유저기준 예약을 찾는다.")
    @Order(2)
    @Test
    void requestReservationByUserId() {
        AuthResponse authResponse = AuthAcceptanceStep.createTokenByLogin(signInRequest);

        ExtractableResponse<Response> makeReservationResponse = ReservationAcceptanceStep.requestReservationByUser(authResponse);
        AcceptanceStep.assertThatStatusIsOk(makeReservationResponse);
    }

    @DisplayName("미팅룸 기준 예약을 찾는다.")
    @Order(3)
    @Test
    void requestReservationByMeetingRoomId() {
        AuthResponse authResponse = AuthAcceptanceStep.createTokenByLogin(signInRequest);

        ExtractableResponse<Response> makeReservationResponse = ReservationAcceptanceStep.requestReservationByRoom(authResponse, 1L);
        AcceptanceStep.assertThatStatusIsOk(makeReservationResponse);
    }

    @DisplayName("동아리방을 예약을 취소한다.")
    @Order(4)
    @Test
    void requestDeleteReservation() {
        AuthResponse authResponse = AuthAcceptanceStep.createTokenByLogin(signInRequest);
        ExtractableResponse<Response> deleteReservationResponse = ReservationAcceptanceStep.requestDeleteReservation(3L, authResponse);
        AcceptanceStep.assertThatStatusIsDeleted(deleteReservationResponse);
    }
}

