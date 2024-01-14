package backend.backend.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SuggestionCommentResponseDto {
    private Long id;
    private String authorName;
    private String context;

    public SuggestionCommentResponseDto (Long id, String authorName, String context) {
        this.id = id;
        this.authorName = authorName;
        this.context = context;
    }
}
