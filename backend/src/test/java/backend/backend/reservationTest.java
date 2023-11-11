package backend.backend;

import backend.backend.meetingroom.entity.MeetingRoom;
import backend.backend.reservation.dto.ReservationRequest;
import backend.backend.reservation.entity.Reservation;
import backend.backend.reservation.repository.ReservationRepository;
import backend.backend.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class reservationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

//    @Mock
//    private MeetingRoomRepository meetingRoomRepository;

    @BeforeEach
    public void setUp() {
        // 테스트용 데이터 설정 또는 Mock 데이터 설정
    }

    @Test
    public void testCreateReservation() throws Exception {
        // 예약 요청 데이터 설정
        ReservationRequest request = new ReservationRequest(2023, 10, 27, LocalDateTime.now(), 4, new MeetingRoom());

        // 예약 서비스 Mock 설정
        when(reservationService.makeReservation(request)).thenReturn(new Reservation());

        // POST 요청을 수행하고 HTTP 상태 코드 201(Created)를 기대
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"year\": 2023, \"month\": 10, \"date\": 27, \"reservationTime\": \"2023-10-27T10:00:00\", \"members\": 4, \"meetingRoom\": { \"id\": 1 } }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllReservations() throws Exception {
        // 예약 목록 데이터 설정
        List<Reservation> reservations = List.of(new Reservation(), new Reservation());

        // 예약 서비스 Mock 설정
        when(reservationService.getAllReservations()).thenReturn(reservations);

        // GET 요청을 수행하고 HTTP 상태 코드 200(OK)를 기대
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservation"))
                .andExpect(status().isOk());
    }
}
