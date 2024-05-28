package backend.backend.report.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportCategory {
    BOARD("게시판"),
    COMMENT("댓글");

    private final String key;
}
