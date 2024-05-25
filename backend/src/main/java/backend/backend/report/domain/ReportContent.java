package backend.backend.report.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportContent {
    REPEAT("도배"),
    INAPPROPRIATE("부적절"),

    ADVERTISEMENT("상업적 광고"),

    PORNOGRAPHY("음란물/블건전"),

    ABUSE("욕설"),

    POLITICAL("정치적"),

    FRAUD("사기");

    private final String value;
}
