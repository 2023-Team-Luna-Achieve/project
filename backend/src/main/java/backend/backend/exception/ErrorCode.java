package backend.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(409, "AU_001", "이미 존재하는 Email입니다."),
    BAD_LOGIN(400, "AU_002", "잘못된 아이디 또는 패스워드입니다."),
    UNAUTHORIZED_EMAIL(400, "AU_003", "인증되지 않은 Email입니다."),
    INVALID_CODE(400, "AU_003", "코드가 옳지 못합니다.");


    private final int status;
    private final String code;
    private final String message;

}
