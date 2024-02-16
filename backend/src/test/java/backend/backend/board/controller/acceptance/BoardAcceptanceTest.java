//package backend.backend.board.controller.acceptance;
//
//import backend.backend.auth.config.util.RedisUtil;
//import backend.backend.auth.dto.EmailRequest;
//import backend.backend.auth.dto.VerificationRequest;
//import backend.backend.board.controller.acceptance.step.BoardAcceptanceStep;
//import backend.backend.common.acceptance.AcceptanceTest;
//import backend.backend.common.acceptance.step.AcceptanceStep;
//import backend.backend.board.entity.Category;
//import backend.backend.user.controller.step.AuthAcceptanceStep;
//import backend.backend.user.controller.step.UserAcceptanceStep;
//import backend.backend.user.dto.AuthResponse;
//import backend.backend.user.dto.SignInRequest;
//import backend.backend.user.dto.SignUpRequest;
//import backend.backend.user.entity.Affiliation;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//
//@DisplayName("Board 인수 테스트")
//@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")
//public class BoardAcceptanceTest extends AcceptanceTest {
//
//    @Autowired
//    RedisUtil redisUtil;
//    private static SignInRequest signInRequest1;
//    private static SignInRequest signInRequest2;
//    private static AuthResponse authResponse1;
//    private static AuthResponse authResponse2;
//
//    @BeforeEach
//    @Override
//    public void setUp() {
//        UserAcceptanceStep.requestEmailVerifiedCodeSend(new EmailRequest("jaeyoon321@naver"));
//        String data = redisUtil.getData("jaeyoon321@naver");
//        UserAcceptanceStep.requestEmailVerified(new VerificationRequest("jaeyoon321@naver", data));
//
//        // 회원가입
//        SignUpRequest signUpRequest1 = new SignUpRequest("유재윤", "jaeyoon321@naver", "1105", Affiliation.Techeer);
//        UserAcceptanceStep.requestSignUp(signUpRequest1);
//
//        // 로그인
//        signInRequest1 = new SignInRequest("jaeyoon321@naver", "1105");
//        authResponse1 = AuthAcceptanceStep.createTokenByLogin(signInRequest1);
//
//
//        // 다른 계정용 테스트
//        UserAcceptanceStep.requestEmailVerifiedCodeSend(new EmailRequest("aaa@naver"));
//        String testData = redisUtil.getData("aaa@naver");
//        UserAcceptanceStep.requestEmailVerified(new VerificationRequest("aaa@naver", testData));
//
//        // 회원가입
//        SignUpRequest signUpRequest2 = new SignUpRequest("aaa", "aaa@naver", "aaa", Affiliation.Techeer);
//        UserAcceptanceStep.requestSignUp(signUpRequest2);
//
//        // 로그인
//        signInRequest2 = new SignInRequest("aaa@naver", "aaa");
//        authResponse2 = AuthAcceptanceStep.createTokenByLogin(signInRequest2);
//    }
//
//    @DisplayName("건의사항 게시글을 생성한다")
//    @Order(1)
//    @Test
//    void requestMakeSuggestionBoard() {
//        SuggestionRequest suggestionRequestDto = new SuggestionRequest("생성 테스트입니다", Category.Suggestion, "생성 테스트 글쓰기 입니다");
//        ExtractableResponse<Response> makeSuggestionBoardResponse = BoardAcceptanceStep.requestMakeSuggestionBoard(suggestionRequestDto, authResponse1);
//        AcceptanceStep.assertThatStatusIsCreated(makeSuggestionBoardResponse);
//    }
//
//    @DisplayName("작성자가 아닌 유저의 계정으로 건의사항 게시글을 수정할 경우 예외를 발생시킨다")
//    @Order(2)
//    @Test
//    void requestUpdateExceptionSuggestionBoard() {
//        SuggestionRequest suggestionRequestDto = new SuggestionRequest("수정 테스트입니다", Category.Suggestion, "수정 테스트 글쓰기 입니다");
//        ExtractableResponse<Response> updateSuggestionBoardResponse = BoardAcceptanceStep.requestUpdateSuggestionBoard(1L, suggestionRequestDto, authResponse2);
//        AcceptanceStep.assertThatStatusIsForbidden(updateSuggestionBoardResponse);
//    }
//
//    @DisplayName("건의사항 게시글을 수정한다")
//    @Order(3)
//    @Test
//    void requestUpdateSuggestionBoard() {
//        SuggestionRequest suggestionRequestDto = new SuggestionRequest("수정 테스트입니다", Category.Suggestion, "수정 테스트 글쓰기 입니다");
//        ExtractableResponse<Response> updateSuggestionBoardResponse = BoardAcceptanceStep.requestUpdateSuggestionBoard(1L, suggestionRequestDto, authResponse1);
//        AcceptanceStep.assertThatStatusIsOk(updateSuggestionBoardResponse);
//    }
//
//    @DisplayName("작성자가 아닌 유저의 계정으로 건의사항 게시글을 삭제할 경우 예외를 발생시킨다.")
//    @Order(4)
//    @Test
//    void requestDeleteExceptionSuggestionBoard() {
//        ExtractableResponse<Response> deleteSuggestionBoardResponse = BoardAcceptanceStep.requestDeleteSuggestionBoard(1L, authResponse2);
//        AcceptanceStep.assertThatStatusIsForbidden(deleteSuggestionBoardResponse);
//    }
//
//    @DisplayName("건의사항 게시글을 삭제한다.")
//    @Order(5)
//    @Test
//    void requestDeleteSuggestionBoard() {
//        ExtractableResponse<Response> deleteSuggestionBoardResponse = BoardAcceptanceStep.requestDeleteSuggestionBoard(1L, authResponse1);
//        AcceptanceStep.assertThatStatusIsDeleted(deleteSuggestionBoardResponse);
//    }
//}
