package backend.backend.board.controller.acceptance.step;

import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.dto.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BoardAcceptanceStep {
    public static ExtractableResponse<Response> requestMakeSuggestionBoard(SuggestionRequest suggestionRequestDto, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(suggestionRequestDto)
                .when()
                .post("/api/suggestion")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestUpdateSuggestionBoard(Long suggestionBoardId, SuggestionRequest suggestionRequestDto, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(suggestionRequestDto)
                .when()
                .patch("/api/suggestion/{suggestionBoardId}", suggestionBoardId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestDeleteSuggestionBoard(Long suggestionBoardId, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/suggestion/{suggestionBoardId}", suggestionBoardId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getSuggestionBoard(Long suggestionBoardId, AuthResponse authResponse) {
        return RestAssured.
                given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .when()
                .get("/api/suggestion/{suggestionBoardId}", suggestionBoardId)
                .then().log().all()
                .extract();
    }
}
