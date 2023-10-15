package backend.backend.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailSendResponse {
    private String message;

    public EmailSendResponse(String message) {
        this.message = message;
    }
}
