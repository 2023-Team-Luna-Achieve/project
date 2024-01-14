package backend.backend.comment.dto;

import backend.backend.comment.entity.NoticeBoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeBoardCommentResponse {
    private Long id;
    private String authorName;
    private String context;

//    public NoticeBoardCommentResponse(NoticeBoardComment) {
//        this.id = id;
//        this.noticeBoardId = noticeBoardId;
//        this.author = author;
//        this.context = context;
//    }

    public static NoticeBoardCommentResponse of(NoticeBoardComment noticeBoardComment) {
        return new NoticeBoardCommentResponse(
                noticeBoardComment.getId(),
                noticeBoardComment.getUser().getName(),
                noticeBoardComment.getContext()
        );
    }
}