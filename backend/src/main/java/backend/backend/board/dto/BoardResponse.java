package backend.backend.board.dto;

import backend.backend.board.entity.Board;
import backend.backend.board.entity.Category;
import backend.backend.common.dto.Identifiable;
import backend.backend.image.domain.BoardImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BoardResponse(
        Long boardId,
        String author,
        String authorEmail,
        Category category,
        String title,
        String context,
        int viewCount,
        int commentCount,
        List<String> imageUrls,
        LocalDateTime createdAt

) implements Identifiable {

    @Override
    public Long getId() {
        return boardId;
    }

    public static BoardResponse from(Board board) {
        List<String> imageUrls = board.getImages()
                .stream()
                .map(BoardImage::getImageUrl)
                .collect(Collectors.toList());

        return new BoardResponse(
                board.getId(),
                board.getUser().getName(),
                board.getUser().getEmail(),
                board.getCategory(),
                board.getTitle(),
                board.getContext(),
                board.getViewCount(),
                board.getComments().size(),
                imageUrls,
                board.getCreatedAt()
        );
    }
}