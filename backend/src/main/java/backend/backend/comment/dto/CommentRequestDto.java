package backend.backend.comment.dto;

import backend.backend.comment.entity.NoticeBoardComment;
import backend.backend.comment.entity.SuggestionComment;
import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.noticeboard.entity.SuggestionBoard;
import backend.backend.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String context;

    public NoticeBoardComment toNoticeComment(User user, NoticeBoard noticeBoard) {
        return NoticeBoardComment.builder()
                .user(user)
                .noticeBoard(noticeBoard)
                .context(context)
                .build();
    }

    public SuggestionComment toSuggestComment(User user, SuggestionBoard suggestionBoard) {
        return SuggestionComment.builder()
                .user(user)
                .suggestionBoard(suggestionBoard)
                .context(context)
                .build();
    }
}