package backend.backend.noticeboardcomment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private Long noticeBoardId;
    private Long userId;
    private String context;
    private LocalDateTime created_at;
    // 추가 필드가 있다면 추가
}