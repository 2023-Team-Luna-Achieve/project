package backend.backend.notification.service.fcm;

import backend.backend.common.exception.BusinessException;
import backend.backend.common.exception.ErrorCode;
import backend.backend.notification.domain.FcmToken;
import backend.backend.notification.domain.FcmNotification;
import backend.backend.notification.dto.CommentNotificationMessage;
import backend.backend.notification.dto.CommentNotificationMessage.Message;
import backend.backend.notification.dto.CommentNotificationMessage.Data;
import backend.backend.notification.dto.CommentNotificationMessage.Notification;
import backend.backend.notification.repository.FcmTokenRepository;

import static backend.backend.config.FcmConfig.FIREBASE_ADMIN_KEY;

import backend.backend.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FcmService {
    private static final String GOOGLE_AUTH_URL = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String FIREBASE_NOTIFICATION_REQUEST_URL = "https://fcm.googleapis.com/v1/projects/%s/messages:send";
    @Value("${fcm.project.id}")
    private String projectId;

    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public void sendMessage(FcmNotification fcmNotification) {
        String message = createMessage(fcmNotification);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(message, headers);
        String requestUrl = String.format(FIREBASE_NOTIFICATION_REQUEST_URL, projectId);
        ResponseEntity<String> exchange = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        if (exchange.getStatusCode().isError()) {
            throw new BusinessException(ErrorCode.FAILED_FCM_REQUEST);
        }
    }

    public String getAccessToken() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FIREBASE_ADMIN_KEY).getInputStream())
                    .createScoped(List.of(GOOGLE_AUTH_URL));
            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new BusinessException(ErrorCode.FAILED_FCM_ACCESS_TOKEN_REQUEST);
        }
    }

    private String createMessage(FcmNotification fcmNotification) {
        Long receiverId = fcmNotification.getReceiverId();
        validateUserUserExist(receiverId);

        FcmToken fcmToken = findFcmToken(receiverId);
        CommentNotificationMessage commentNotificationMessage = CommentNotificationMessage.of(
                false,
                Message.of(fcmToken.getToken(),
                        Notification.of(
                                fcmNotification.getFcmNotificationCategory().getValue(),
                                fcmNotification.getContent()
                        ),

                        Data.from(fcmNotification)));
        try {
            return objectMapper.writeValueAsString(commentNotificationMessage);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.FAILED_JSON_CONVERT);
        }
    }

    private void validateUserUserExist(Long receiverId) {
        if (!userRepository.existsById(receiverId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
    }

    private FcmToken findFcmToken(Long receiverId) {
        return fcmTokenRepository.findByUserId(receiverId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FCM_TOKEN_NOT_FOUND));
    }
}
