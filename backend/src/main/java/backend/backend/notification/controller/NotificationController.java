package backend.backend.notification.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.common.dto.SingleRecordResponse;
import backend.backend.notification.dto.FcmNotificationResponse;
import backend.backend.notification.service.NotificationService;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<SingleRecordResponse<FcmNotificationResponse>> getAllNotifications(@CurrentUser User user,
                                                                                             @RequestParam(required = false) String cursor) {
        return ResponseEntity.ok().body(notificationService.getNotifications(user, cursor));
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<Void> readNotification(@PathVariable Long notificationId) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}
