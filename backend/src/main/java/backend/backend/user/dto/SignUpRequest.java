package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.AuthProvider;
import backend.backend.user.entity.Role;
import backend.backend.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @Email
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

//    @NotEmpty(message = "소속은 공백일 수 없습니다.")
    private Affiliation affiliation;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .affiliation(affiliation)
                .provider(AuthProvider.local)
                .role(Role.ROLE_USER)
                .build();
    }

    public SignUpRequest encryptPassword(PasswordEncoder passwordEncoder) {
        String encryptedPassword = passwordEncoder.encode(this.password);
        return new SignUpRequest(this.name, this.email, encryptedPassword, this.affiliation);
    }
}
