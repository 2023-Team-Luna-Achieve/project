package backend.backend.noticeboard.dto;

import backend.backend.noticeboard.entity.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SuggestionRequestDto {
    private String title;
    private Category category;
    private String context;
}