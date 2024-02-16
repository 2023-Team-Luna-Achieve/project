package backend.backend.board.dto;

import backend.backend.board.entity.Board;
import backend.backend.board.entity.Category;
import backend.backend.user.entity.User;

public record BoardRequest(
        String title,
        Category category,
        String context
) {
    public Board toEntity(User user, Long boardsSequenceNumber) {
        return Board.builder()
                .sequenceNumber(boardsSequenceNumber)
                .user(user)
                .title(title)
                .category(category)
                .context(context)
                .build();
    }
}