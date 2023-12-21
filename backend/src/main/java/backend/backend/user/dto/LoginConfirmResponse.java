package backend.backend.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginConfirmResponse {
    private String message;
    private boolean loggedIn;

    public LoginConfirmResponse(String message, boolean loggedIn) {
        this.message = message;
        this.loggedIn = loggedIn;
    }
}
