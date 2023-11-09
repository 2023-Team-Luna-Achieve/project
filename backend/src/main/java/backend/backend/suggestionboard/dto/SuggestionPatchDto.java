package backend.backend.suggestionboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SuggestionPatchDto {
    private String title;
    private String body;
/* 건의사항 태그용?
    private List<String> tags;
*/
}