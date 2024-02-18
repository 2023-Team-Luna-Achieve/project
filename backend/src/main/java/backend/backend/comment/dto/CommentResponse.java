package backend.backend.comment.dto;

import backend.backend.comment.entity.Comment;

public record CommentResponse(
    Long id,
    Long sequenceNumber,
    String authorName,
    String context
) {

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getSequenceNumber(),
                comment.getUser().getName(),
                comment.getContext()
        );
    }
}