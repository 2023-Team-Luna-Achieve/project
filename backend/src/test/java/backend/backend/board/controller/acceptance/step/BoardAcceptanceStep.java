package backend.backend.board.controller.acceptance.step;

import backend.backend.board.dto.BoardRequest;
import backend.backend.user.controller.step.AuthAcceptanceStep;
import backend.backend.user.dto.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BoardAcceptanceStep {
    public static ExtractableResponse<Response> requestCreateBoard(BoardRequest boardRequest, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(boardRequest)
                .when()
                .post("/api/boards")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestUpdateBoard(Long boardId, BoardRequest boardRequest, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(boardRequest)
                .when()
                .patch("/api/boards/{boardId}", boardId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestDeleteBoard(Long boardId, AuthResponse authResponse) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/boards/{boardId}", boardId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getBoard(Long boardId, AuthResponse authResponse) {
        return RestAssured.
                given().log().all()
                .header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
                .when()
                .get("/api/boards/{boardId}", boardId)
                .then().log().all()
                .extract();
    }
}
