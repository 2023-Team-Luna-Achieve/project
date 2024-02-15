package backend.backend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignOutResponse {
    private String message;

    public SignOutResponse(String message) {
        this.message = message;
    }
}
