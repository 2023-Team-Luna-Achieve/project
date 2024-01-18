package backend.backend.user.controller;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.common.acceptance.AcceptanceTest;
import backend.backend.common.acceptance.step.AcceptanceStep;
import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.controller.step.UserAcceptanceStep;
import backend.backend.user.dto.SignInRequest;
import backend.backend.user.dto.SignUpRequest;
import backend.backend.user.entity.Affiliation;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class AuthAcceptanceTest extends AcceptanceTest {
    @Autowired
    private RedisUtil redisUtil;
    private static SignUpRequest signUpRequest;
    private static SignInRequest signInRequest;

    @Override
    @BeforeEach
    public void setUp() {
        signUpRequest = new SignUpRequest("유재윤", "jaeyoon321@naver", "1105", Affiliation.Techeer);

        signInRequest = new SignInRequest("jaeyoon321@naver", "1105");
    }

    @DisplayName("로그인을 요청한다")
    @Test
    void requestSignin() {
        //given
        // 이메일 인증
        UserAcceptanceStep.requestEmailVerifiedCodeSend(new EmailRequest("jaeyoon321@naver"));
        String data = redisUtil.getData("jaeyoon321@naver");
        UserAcceptanceStep.requestEmailVerified(new VerificationRequest("jaeyoon321@naver", data));

        //회원가입
        UserAcceptanceStep.requestSignUp(signUpRequest);

        //when
        ExtractableResponse<Response> signInStatus = AuthAcceptanceStep.requestLogin(signInRequest);

        //then
        AcceptanceStep.assertThatStatusIsOk(signInStatus);
    }
}

