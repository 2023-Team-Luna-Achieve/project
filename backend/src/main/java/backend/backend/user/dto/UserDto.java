package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.Auth;
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
    private Auth auth;
    private Affiliation affiliation;

    @Builder
    public UserDto(Long id, String name, String email, Auth auth, Affiliation affiliation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.auth = auth;
        this.affiliation = affiliation;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getAuth(), user.getAffiliation());
    }
}