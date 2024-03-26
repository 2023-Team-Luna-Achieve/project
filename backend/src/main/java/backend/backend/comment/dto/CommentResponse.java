package backend.backend.comment.dto;

import backend.backend.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
    Long id,
    Long sequenceNumber,
    String authorName,
    String context,
    LocalDateTime createdAt
) {

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getSequenceNumber(),
                comment.getUser().getName(),
                comment.getContext(),
                comment.getCreatedAt()
        );
    }
}