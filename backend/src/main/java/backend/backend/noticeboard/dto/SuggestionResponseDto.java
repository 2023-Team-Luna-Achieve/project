package backend.backend.noticeboard.dto;

import backend.backend.noticeboard.entity.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SuggestionResponseDto {
    private Long id;
    private String author;
    private String title;
    private Category category;
    private String context;
    private int viewCount;

    public SuggestionResponseDto(Long id, String name, String title, Category category, String context) {
        this.id = id;
        this.author = name;
        this.title = title;
        this.category = category;
        this.context = context;
    }
}
