package backend.backend.notification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_token_id")
    private Long id;

    private String token;

    private Long userId;

    public FcmToken( Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public static FcmToken createFcmToken(Long userId, String token) {
        return new FcmToken(userId, token);
    }

    public void update(String token) {
        this.token = token;
    }
}
