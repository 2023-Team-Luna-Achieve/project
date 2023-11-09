package backend.backend.suggestionboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SuggestionPostDto {
    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String body;

    /* 건의사항 태그용?
    private List<String> tags;
    */
}