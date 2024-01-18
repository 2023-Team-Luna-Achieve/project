package backend.backend.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest {
    private String email;
    private String code;
}
