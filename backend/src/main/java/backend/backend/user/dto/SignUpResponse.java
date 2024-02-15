package backend.backend.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponse {
    private int status;
    private boolean success;
    private String message;
    private UserDto user;

    public SignUpResponse(int status, boolean success, String message, UserDto user) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.user = user;
    }
}
