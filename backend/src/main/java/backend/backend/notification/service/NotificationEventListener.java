package backend.backend.notification.service;

import backend.backend.common.event.CommentCreateEvent;
import backend.backend.common.event.ReservationReminderEvent;
import backend.backend.notification.domain.FcmNotification;
import backend.backend.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    @Async //***
    public void sendCreateCommentNotification(CommentCreateEvent commentCreateEvent) {
        FcmNotification fcmNotification = FcmNotification.builder()
                .content(commentCreateEvent.senderName() + "님이 댓글을 작성했습니다: " + commentCreateEvent.content())
                .targetId(commentCreateEvent.targetId())
                .receiverId(commentCreateEvent.receiverId())
                .build();

        notificationRepository.save(fcmNotification);
        fcmService.sendMessage(fcmNotification);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    @Async
    public void sendReservationReminderNotification(ReservationReminderEvent reservationReminderEvent) {
        FcmNotification fcmNotification = FcmNotification.builder()
                .content(reservationReminderEvent.receiverName() + "님 입장 10분 전 입니다!")
                .targetId(reservationReminderEvent.targetId())
                .receiverId(reservationReminderEvent.receiverId())
                .build();

        notificationRepository.save(fcmNotification);
        fcmService.sendMessage(fcmNotification);
    }
}
