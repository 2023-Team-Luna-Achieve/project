package backend.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(409, "AU_001", "이미 존재하는 Email입니다."),
    BAD_LOGIN(400, "AU_002", "잘못된 아이디 또는 패스워드입니다."),
    UNAUTHORIZED_EMAIL(400, "AU_003", "인증되지 않은 Email입니다."),
    INVALID_CODE(400, "AU_004", "코드가 옳지 못합니다."),
    ALREADY_VERIFIED_EMAIL(400, "AU_005", "이미 인증된 이메일입니다."),
    NEED_LOGIN(401, "AU_006", "로그인이 필요합니다"),
    NOT_LOGIN(401, "AU_007", "로그인이 되어있지 않습니다"),
    NOT_FOUND(404, "AU_008","유저가 존재하지 않습니다");


    private final int status;
    private final String code;
    private final String message;

}
