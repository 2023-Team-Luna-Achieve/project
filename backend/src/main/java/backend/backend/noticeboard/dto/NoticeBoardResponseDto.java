package backend.backend.noticeboard.dto;

import backend.backend.noticeboard.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoticeBoardResponseDto {
    private Long id;
    private String author;
    private String title;
    private Category category;
    private String context;
    private int viewCount;


    @Getter
    @Setter
    public static class PagedNoticeBoardResponseDto { //커서 페이지네이션
        private List<NoticeBoardResponseDto> contents;
        private long totalElements;
        private long nextCursor;

    }

    public NoticeBoardResponseDto(Long id, String author, String title, Category category, String context, int viewCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.context = context;
        this.viewCount = viewCount;
    }
}