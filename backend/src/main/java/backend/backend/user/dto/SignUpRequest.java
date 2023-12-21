package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.Role;
import backend.backend.user.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;
//    @NotBlank(message = "소속은 공백일 수 없습니다.")
    private Affiliation affiliation;

    public SignUpRequest(String name, String email, String password, Affiliation affiliation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .affiliation(affiliation)
                .role(Role.ROLE_USER)
                .created_at(LocalDateTime.now())
                .build();
    }

    public SignUpRequest encryptPassword(BCryptPasswordEncoder encoder) {
        String encryptedPassword = encoder.encode(this.password);
        return new SignUpRequest(this.name, this.email, encryptedPassword, this.affiliation);
    }
}
