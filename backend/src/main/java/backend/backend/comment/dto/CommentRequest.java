package backend.backend.comment.dto;

import backend.backend.comment.entity.Comment;
import backend.backend.board.entity.Board;
import backend.backend.user.entity.User;

public record CommentRequest(
        String context
) {

    public Comment toEntity(User user, Board board, Long sequenceNumber) {
        return Comment.builder()
                .user(user)
                .sequenceNumber(sequenceNumber)
                .board(board)
                .context(context)
                .build();
    }
}