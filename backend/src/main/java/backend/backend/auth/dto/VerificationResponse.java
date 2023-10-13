package backend.backend.auth.dto;

import backend.backend.user.dto.SignUpResponse;
import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.Auth;
import backend.backend.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
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
