package backend.backend.noticeboard.dto;

import backend.backend.noticeboard.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBoardDto {
    private Long id;
    private String title;
    private Category category;
    private String context;
    private int viewCount;
    // 다른 필요한 필드들을 추가할 수 있습니다.

    // 생성자, 게터, 세터 등 필요한 메서드를 추가할 수 있습니다.
}
