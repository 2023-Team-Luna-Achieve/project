package backend.backend.notification.repository;

import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.notification.dto.FcmNotificationResponse;

public interface FcmNotificationRepositoryCustom {
    SingleRecordResponse<FcmNotificationResponse> findNotifications(Long userId, String cursor);
}
