package backend.backend.comment.dto;

import backend.backend.comment.entity.Comment;
import backend.backend.common.dto.Identifiable;
import backend.backend.user.entity.Affiliation;

import java.time.LocalDateTime;

public record CommentResponse(
    Long commentId,
    String authorName,
    String email,
    Affiliation affiliation,
    String context,
    LocalDateTime createdAt
) implements Identifiable  {
    @Override
    public Long getId() {
        return commentId;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getName(),
                comment.getUser().getEmail(),
                comment.getUser().getAffiliation(),
                comment.getContext(),
                comment.getCreatedAt()
        );
    }
}