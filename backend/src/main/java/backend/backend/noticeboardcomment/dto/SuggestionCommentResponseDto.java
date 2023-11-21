package backend.backend.noticeboardcomment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SuggestionCommentResponseDto {
    private Long id;
    private Long suggestionId;
    private Long userId;
    private String context;
    private LocalDateTime created_at;
}
