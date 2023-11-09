package backend.backend.suggestionboard.dto;

//import

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SuggestionResponseDto {
    private Long id; //유저아이디?이메일? 다른 정보가 있는가?
    private String title;
    private String body;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    //private List<String> tags;  //건의사항 게시글 태그용?
}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }