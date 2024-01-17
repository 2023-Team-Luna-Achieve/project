package backend.backend.user.controller.step;

import backend.backend.user.dto.AuthResponse;
import backend.backend.user.dto.SignInRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AuthAcceptanceStep {
    public static ExtractableResponse<Response> requestLogin(SignInRequest signInRequest) {
        return RestAssured.
                given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signInRequest)
                .when()
                .post("/api/user/sign-in")
                .then().log().all()
                .extract();
    }

    public static AuthResponse createTokenByLogin(SignInRequest signInRequest) {
        ExtractableResponse<Response> response = requestLogin(signInRequest);
        return new AuthResponse(response.header(HttpHeaders.AUTHORIZATION).substring(7));
    }
}
