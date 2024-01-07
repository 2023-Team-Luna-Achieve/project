package backend.backend.noticeboardcomment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private Long noticeBoardId;
    private String author;
    private String context;
    private LocalDateTime created_at;

    public CommentResponse(Long id, Long noticeBoardId, String author, String context, LocalDateTime created_at) {
        this.id = id;
        this.noticeBoardId = noticeBoardId;
        this.author = author;
        this.context = context;
        this.created_at = created_at;
    }
}