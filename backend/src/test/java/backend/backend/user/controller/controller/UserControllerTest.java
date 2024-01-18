package backend.backend.user.controller.controller;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.controller.EmailController;
import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.auth.jwt.filter.JwtExtractUtil;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.service.EmailService;
import backend.backend.auth.service.RefreshTokenService;
import backend.backend.common.WithMockCustomUser;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.controller.ReservationController;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.service.ReservationService;
import backend.backend.user.controller.UserController;
import backend.backend.user.dto.SignUpRequest;
import backend.backend.user.entity.Affiliation;
import backend.backend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {EmailController.class, UserController.class, ReservationController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest {
    @MockBean
    UserService userService;

    @MockBean
    EmailService emailService;

    @MockBean
    RedisUtil redisUtil;

    @MockBean
    JwtExtractUtil jwtExtractUtil;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    RefreshTokenService refreshTokenService;

    @MockBean
    ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("동아리방 예약을 조회한다.")
    @WithMockCustomUser
    @Test
    void searchReservation() throws Exception {

        List<ReservationResponse> reservationResponse = Arrays.asList(
                new ReservationResponse(1L, "2342:43:32", "2344:3:32", 5, new MeetingRoom("df")));

        given(reservationService.getReservationsByUserId(1L)).willReturn(reservationResponse);

        mockMvc.perform(get("/api/reservation/check").accept(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer Token"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원가입을 한다")
    @WithMockCustomUser
    @Test
    void registerUser() throws Exception {
        EmailRequest emailRequest = new EmailRequest("222@naver.com");
        mockMvc.perform(post("/api/email/verification/request")
                        .content(objectMapper.writeValueAsBytes(emailRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        when(emailService.sendEmailIfNotExists(emailRequest.getEmail())).thenReturn(new EmailSendResponse("됨"));

        String code = redisUtil.getData(emailRequest.getEmail());
        VerificationRequest verificationRequest = new VerificationRequest(emailRequest.getEmail(), code);
        when(emailService.verifyEmail(emailRequest.getEmail(), code)).thenReturn(new VerificationResponse("됐나"));

        mockMvc.perform(post("/api/email/verification/confirm")
                        .content(objectMapper.writeValueAsBytes(verificationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());

        SignUpRequest signUpRequest = new SignUpRequest("라면2", "라면@naver.com", "라면2", Affiliation.Techeer);
        when(userService.createUserIfEmailNotExists(signUpRequest)).thenReturn(1L);
        mockMvc.perform(post("/api/user/sign-up")
                        .content(objectMapper.writeValueAsBytes(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
