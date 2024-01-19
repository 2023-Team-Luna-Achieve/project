package backend.backend.board.controller.acceptance.step;

import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.dto.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BoardAcceptanceStep {
    public static ExtractableResponse<Response> requestMakeSuggestionBoard(ReservationRequest reservationRequest, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequest)
                .when()
                .post("/api/suggestion")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestDeleteSuggestionBoard(Long reservationId, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/suggestion", reservationId)
                .then().log().all()
                .extract();
    }


    public static ExtractableResponse<Response> getSuggestionBoards(AuthResponse authResponse) {
        return RestAssured.
                given().log().all()
                .header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
                .when()
                .get("/api/suggestions")
                .then().log().all()
                .extract();
    }
}
