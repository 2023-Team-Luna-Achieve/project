package backend.backend.noticeboard.dto;

import backend.backend.noticeboard.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBoardRequestDto {
    private String title;
    private Category category;
    private String context;
}