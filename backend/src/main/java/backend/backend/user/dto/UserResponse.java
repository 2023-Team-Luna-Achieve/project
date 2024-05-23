package backend.backend.user.dto;

import backend.backend.user.entity.Affiliation;
import backend.backend.user.entity.Role;
import backend.backend.user.entity.User;

public record UserResponse(
        String name,
        String email,
        Affiliation affiliation,
        String userRole
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getAffiliation(),
                user.roleName()
        );
    }
}