package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.Role;
import backend.backend.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Affiliation affiliation;

    @Builder
    public UserDto(Long id, String name, String email, Role role, Affiliation affiliation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.affiliation = affiliation;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getAffiliation());
    }
}