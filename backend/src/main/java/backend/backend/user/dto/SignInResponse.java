package backend.backend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {
    private String message;
    private UserDto user;

    public SignInResponse(String message, UserDto user) {
        this.message = message;
        this.user = user;
    }
}
