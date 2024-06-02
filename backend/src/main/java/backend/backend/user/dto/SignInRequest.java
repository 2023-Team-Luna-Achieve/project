package backend.backend.user.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "email은 비어있을 수 없습니다")
        String email,
        @NotBlank(message = "password은 비어있을 수 없습니다")
        String password
) {
}
