package backend.backend.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBoardCommentResponse {
    private Long id;
    private Long noticeBoardId;
    private String author;
    private String context;

    public NoticeBoardCommentResponse(Long id, Long noticeBoardId, String author, String context) {
        this.id = id;
        this.noticeBoardId = noticeBoardId;
        this.author = author;
        this.context = context;
    }
}