package backend.backend.user.dto;

public record PasswordUpdateRequest(
        String originalPassword,
        String requestPassword
) {
}
