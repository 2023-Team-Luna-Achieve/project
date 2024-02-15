package backend.backend.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationResponse {
    private String message;

    public VerificationResponse(String message) {
        this.message = message;
    }
}
