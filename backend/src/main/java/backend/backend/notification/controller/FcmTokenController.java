package backend.backend.notification.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.notification.dto.FcmAccessTokenCreateRequest;
import backend.backend.notification.service.FcmTokenService;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class FcmTokenController {
    private final FcmTokenService fcmTokenService;
    @PostMapping("/token")
    public ResponseEntity<Void> createAccessToken(@CurrentUser User user,
                                                  @RequestBody FcmAccessTokenCreateRequest request) {
        fcmTokenService.save(user, request);
        return ResponseEntity.ok().build();
    }
}
