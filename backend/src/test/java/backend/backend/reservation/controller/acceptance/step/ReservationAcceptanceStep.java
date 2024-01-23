package backend.backend.reservation.controller.acceptance.step;

import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.dto.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class ReservationAcceptanceStep {
    public static ExtractableResponse<Response> requestMakeReservation(ReservationRequest reservationRequest, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequest)
                .when()
                .post("/api/reservation")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestDeleteReservation(Long reservationId, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/reservation/{reservationId}", reservationId)
                .then().log().all()
                .extract();

    }

    public static ExtractableResponse<Response> requestReservationByUser(AuthResponse authResponse) {
        return RestAssured.
                given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .when()
                .get("/api/reservation/my")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestReservationByRoom(AuthResponse authResponse, Long meetingRoomId) {
        return RestAssured.
                given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .when()
                .get("/api/reservation/meetingroom/{meetingRoomId}", meetingRoomId)
                .then().log().all()
                .extract();
    }
}
