package backend.backend.board.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    SUGGESTION("SUGGESTION", "제안 게시판"),
    NOTICE("NOTICE", "공지 게시판"),
    LOST_ITEM("LOST_ITEM", "분실물 게시판");

    private String key;
    private String value;
}