package backend.backend.user.controller;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.common.acceptance.AcceptanceTest;
import backend.backend.common.acceptance.step.AcceptanceStep;
import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.controller.step.UserAcceptanceStep;
import backend.backend.user.dto.AuthResponse;
import backend.backend.user.dto.SignInRequest;
import backend.backend.user.dto.SignUpRequest;
import backend.backend.user.entity.Affiliation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("User 관련 테스트")
@ActiveProfiles("test")
class UserAcceptanceTest extends AcceptanceTest {
    @Autowired
    private RedisUtil redisUtil;
    private static SignUpRequest signUpRequest;
    private static SignInRequest signInRequest;
    private static AuthResponse authResponse;

    @BeforeEach
    @Override
    public void setUp() {

        UserAcceptanceStep.requestEmailVerifiedCodeSend(new EmailRequest("jaeyoon321@naver"));
        String data = redisUtil.getData("jaeyoon321@naver");
        UserAcceptanceStep.requestEmailVerified(new VerificationRequest("jaeyoon321@naver", data));

        // 회원가입
        signUpRequest = new SignUpRequest("유재윤", "jaeyoon321@naver", "1105", Affiliation.Techeer);
        UserAcceptanceStep.requestSignUp(signUpRequest);

        // 로그인
        signInRequest = new SignInRequest("jaeyoon321@naver", "1105");
        authResponse = AuthAcceptanceStep.createTokenByLogin(signInRequest);
    }

    @DisplayName("동아리방 예약을 확인한다.")
    @Test
    void getReservation() {
        //when
        ExtractableResponse<Response> reservationCheckStatus = UserAcceptanceStep.getReservation(authResponse);

        //then
        AcceptanceStep.assertThatStatusIsOk(reservationCheckStatus);
    }

    @DisplayName("회원가입을 요청한다")
    @Test
    void requestSignUp() {
        //given
        // 이메일 인증
        UserAcceptanceStep.requestEmailVerifiedCodeSend(new EmailRequest("1@naver"));
        String data = redisUtil.getData("1@naver");
        UserAcceptanceStep.requestEmailVerified(new VerificationRequest("1@naver", data));

        signUpRequest = new SignUpRequest("홍두깨비", "1@naver", "password", Affiliation.Techeer);
        //when
        ExtractableResponse<Response> signUpStatus = UserAcceptanceStep.requestSignUp(signUpRequest);

        //then
        AcceptanceStep.assertThatStatusIsCreated(signUpStatus);
    }
}
