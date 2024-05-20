package backend.backend.auth.dto;

import jakarta.validation.constraints.Email;

public record EmailRequest(
        @Email
        String email
) {
}
