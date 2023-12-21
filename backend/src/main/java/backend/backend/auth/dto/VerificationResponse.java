package backend.backend.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerificationResponse {
    private String message;

    public VerificationResponse(String message) {
        this.message = message;
    }
}
