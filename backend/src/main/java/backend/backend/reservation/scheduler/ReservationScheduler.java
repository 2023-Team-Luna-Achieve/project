package backend.backend.reservation.scheduler;

import backend.backend.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 50 * * * *")
    public void reservationNotification() {
        reservationService.reservationReminderNotification();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void deleteReservation() {
        reservationService.deleteExpiredReservations();
    }
}
