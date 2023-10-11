package backend.backend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {
    @NotBlank(message = "email은 비어있을 수 없습니다")
    private String email;
    @NotBlank(message = "password은 비어있을 수 없습니다")
    private String password;
}
