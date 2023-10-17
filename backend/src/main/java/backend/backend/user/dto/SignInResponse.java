package backend.backend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {
    private int status;
    private boolean success;
    private String message;
    private UserDto user;

    public SignInResponse(int status, boolean success, String message, UserDto user) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.user = user;
    }
}
