package backend.backend.user.dto;

public record PasswordResetRequest(
        String email,
        String newPassword
) {
}
