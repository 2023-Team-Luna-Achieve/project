package backend.backend.report.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportedUserGeneratedCategory {
    BOARD("게시판"),
    COMMENT("댓글");

    private final String key;
}
