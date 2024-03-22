package backend.backend.reservation.controller.controller;

import backend.backend.common.WithMockCustomUser;
import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.controller.ReservationController;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.dto.ReservationResponse;
import backend.backend.reservation.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockCustomUser
    @DisplayName("예약 생성")
    @Test
    void makeReservation() throws Exception {
        LocalDateTime startTime = LocalDateTime.parse("2023-02-09T15:49:52.978Z", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endTime = LocalDateTime.parse("2023-02-10T17:49:52.978Z", DateTimeFormatter.ISO_DATE_TIME);
        ReservationRequest reservationRequest = new ReservationRequest(startTime, endTime, 5, 1L);

        given(reservationService.makeReservation(any(), any())).willReturn(1L);

        mockMvc.perform(post("/api/reservation")
                        .content(objectMapper.writeValueAsString(reservationRequest))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer Token"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/reservation/1"))
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("예약 전체 조회")
    @Test
    void findAllReservation() throws Exception {
        ReservationResponse reservation = new ReservationResponse(1L, "2024년:01월:26일 10:00:00", "2024년:01월:27일 10:00:00", 5, new MeetingRoom("Paloalto"));
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationResponse.add(reservation);

        given(reservationService.getAllReservations()).willReturn(reservationResponse);

        mockMvc.perform(get("/api/reservation/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer Token"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("유저 기준 조회")
    @Test
    void findReservationByUser() throws Exception {
        ReservationResponse reservation = new ReservationResponse(1L, "2024년:01월:26일 10:00:00", "2024년:01월:27일 10:00:00", 5, new MeetingRoom("Palo Alto"));
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationResponse.add(reservation);

        given(reservationService.getReservationsByUserId(1L)).willReturn(reservationResponse);

        mockMvc.perform(get("/api/reservation/my")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer Token"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() throws Exception {
        doNothing().when(reservationService).cancelReservation(any(), anyLong());
        mockMvc.perform(delete("/api/reservation/{id}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer Token"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
