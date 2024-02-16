package backend.backend.board.dto;

import backend.backend.board.entity.Board;
import backend.backend.board.entity.Category;

public record BoardResponse(
        Long boardId,
        Long sequenceNumber,
        String author,
        Category category,
        String title,
        String context,
        int viewCount
) {
    public static BoardResponse from(Board Board) {
        return new BoardResponse(
                Board.getId(),
                Board.getSequenceNumber(),
                Board.getUser().getName(),
                Board.getCategory(),
                Board.getTitle(),
                Board.getContext(),
                Board.getViewCount()
        );
    }
}