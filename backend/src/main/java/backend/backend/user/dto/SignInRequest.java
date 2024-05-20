package backend.backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignInRequest {
    @NotBlank(message = "email은 비어있을 수 없습니다")
    private String email;
    @NotBlank(message = "password은 비어있을 수 없습니다")
    private String password;
}
