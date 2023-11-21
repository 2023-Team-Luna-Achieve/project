package backend.backend.noticeboardcomment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionCommentRequestDto {
    private Long suggestionId;
    private Long userId;
    private String context;
}
