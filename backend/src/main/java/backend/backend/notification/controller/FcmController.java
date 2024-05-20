package backend.backend.notification.controller;

import backend.backend.auth.jwt.CurrentUser;
import backend.backend.notification.dto.FcmAccessTokenCreateRequest;
import backend.backend.notification.service.fcm.FcmTokenService;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
public class FcmController {
    private final FcmTokenService fcmTokenService;

    @PostMapping("/token")
    public ResponseEntity<Void> createAccessToken(@CurrentUser User user,
                                                  @RequestBody FcmAccessTokenCreateRequest request) {
        fcmTokenService.save(user, request);
        return ResponseEntity.ok().build();
    }
}
