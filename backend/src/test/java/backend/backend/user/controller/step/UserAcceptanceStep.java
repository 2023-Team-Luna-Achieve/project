package backend.backend.user.controller.step;

import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.user.dto.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class UserAcceptanceStep {
    public static ExtractableResponse<Response> requestEmailVerifiedCodeSend(EmailRequest emailRequest) {
        return RestAssured.
                given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(emailRequest)
                .when()
                .post("/api/email/verification/request")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestEmailVerified(VerificationRequest verificationRequest) {
        return RestAssured.
                given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(verificationRequest)
                .when()
                .post("/api/email/verification/confirm")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestSignUp(SignUpRequest signUpRequest) {
        return RestAssured.
                given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when()
                .post("/api/user/sign-up")
                .then().log().all()
                .extract();
    }
}
