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
public class SignUpResponse {
    private Long id;
    private String name;
    private String email;
    private Auth auth;
    private Affiliation affiliation;

    @Builder
    public SignUpResponse(Long id, String name, String email, Auth auth, Affiliation affiliation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.auth = auth;
        this.affiliation = affiliation;
    }

    public static SignUpResponse of(User user) {
        return new SignUpResponse(user.getId(), user.getName(), user.getEmail(), user.getAuth(), user.getAffiliation());
    }

}
