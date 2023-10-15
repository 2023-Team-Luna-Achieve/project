package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.Auth;
import backend.backend.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;
//    @NotBlank(message = "소속은 공백일 수 없습니다.")
    private Affiliation affiliation;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .affiliation(affiliation)
                .auth(Auth.General)
                .created_at(LocalDateTime.now())
                .build();
    }
}
