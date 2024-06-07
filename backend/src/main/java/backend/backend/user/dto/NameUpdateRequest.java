package backend.backend.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NameUpdateRequest(
        @Size(min = 2, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "이름은 2 ~ 15자 사이입니다")
        String requestName
) {
}