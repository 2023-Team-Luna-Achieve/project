package backend.backend.comment.dto;

import backend.backend.comment.entity.Comment;
import backend.backend.board.entity.Board;
import backend.backend.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CommentRequest(
        Long boardId,

        @NotEmpty
        @NotBlank
        @Pattern(regexp = "^\\S+$", message = "이름은 공백을 포함할 수 없습니다.")
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