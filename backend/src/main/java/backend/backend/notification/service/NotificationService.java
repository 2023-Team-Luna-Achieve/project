package backend.backend.notification.service;

import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.NotFoundException;
import backend.backend.notification.domain.FcmNotification;
import backend.backend.notification.dto.FcmNotificationResponse;
import backend.backend.notification.repository.FcmNotificationRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmNotificationRepository fcmNotificationRepository;

    public SingleRecordResponse<FcmNotificationResponse> getNotifications(User user, String cursor) {
        return fcmNotificationRepository.getNotifications(user.getId(), cursor);
    }

    @Transactional
    public void readNotification(Long notificationId) {
        FcmNotification fcmNotification = fcmNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
        fcmNotification.read();
    }
}
