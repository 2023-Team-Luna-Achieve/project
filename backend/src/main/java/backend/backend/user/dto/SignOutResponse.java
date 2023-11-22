package backend.backend.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignOutResponse {
    private String message;

    public SignOutResponse(String message) {
        this.message = message;
    }
}
