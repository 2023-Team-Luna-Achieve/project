package backend.backend.comment.dto;

import backend.backend.comment.entity.SuggestionComment;
import backend.backend.noticeboard.entity.SuggestionBoard;
import backend.backend.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String context;

    public SuggestionComment toEntity(User user, SuggestionBoard suggestionBoard) {
        return SuggestionComment.builder()
                .user(user)
                .suggestionBoard(suggestionBoard)
                .context(context)
                .build();
    }
}