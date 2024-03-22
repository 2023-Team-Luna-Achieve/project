package backend.backend.board.dto;

import backend.backend.board.entity.Board;
import backend.backend.board.entity.Category;

import java.time.LocalDateTime;

public record BoardResponse(
        Long boardId,
        Long sequenceNumber,
        String title,
        String context,
        int viewCount,
        int commentCount,
        LocalDateTime createdAt
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getSequenceNumber(),
                board.getTitle(),
                board.getContext(),
                board.getViewCount(),
                board.getComments().size(),
                board.getCreatedAt()
        );
    }
}