package backend.backend.auth.dto;

import jakarta.validation.constraints.Email;

public record VerificationRequest(

        @Email
        String email,
        String code
) {
}
