package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.User;

public record UserResponse(
        String email,
        Affiliation affiliation
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getAffiliation()
        );
    }
}