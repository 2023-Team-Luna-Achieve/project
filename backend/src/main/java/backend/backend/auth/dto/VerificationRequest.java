package backend.backend.auth.dto;

public record VerificationRequest(
    String email,
    String code
) {
}
