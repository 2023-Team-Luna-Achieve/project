package backend.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FcmNotificationCategory {
    SUGGESTION("SUGGESTION", "일반 게시판"),
    NOTICE("NOTICE", "공지 게시판"),
    LOST_ITEM("LOST_ITEM", "분실물 게시판"),
    RESERVATION("RESERVATION", "예약");

    private String key;

    @Getter
    private String value;
}
