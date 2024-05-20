package backend.backend.board.dto;

import backend.backend.board.entity.Board;
import backend.backend.board.entity.Category;

import java.time.LocalDateTime;

public record BoardResponse(
        Long boardId,
        String author,
        String authorEmail,
        Category category,
        String title,
        String context,
        int viewCount,
        int commentCount,
        LocalDateTime createdAt
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getUser().getName(),
                board.getUser().getEmail(),
                board.getCategory(),
                board.getTitle(),
                board.getContext(),
                board.getViewCount(),
                board.getComments().size(),
                board.getCreatedAt()
        );
    }
}