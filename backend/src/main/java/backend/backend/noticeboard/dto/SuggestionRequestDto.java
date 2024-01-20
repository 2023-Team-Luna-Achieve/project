package backend.backend.noticeboard.dto;

import backend.backend.noticeboard.entity.Category;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionRequestDto {
    private String title;
    private Category category;
    private String context;
}