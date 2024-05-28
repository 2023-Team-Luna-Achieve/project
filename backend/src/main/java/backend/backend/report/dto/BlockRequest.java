package backend.backend.report.dto;

import backend.backend.report.domain.Block;
import backend.backend.user.entity.User;

public record BlockRequest(
        String blockedUserEmail
) {
    public Block toEntity(User blocker, User blockedUser) {
        return Block.builder()
                .blocker(blocker)
                .blockedUser(blockedUser)
                .build();
    }
}
