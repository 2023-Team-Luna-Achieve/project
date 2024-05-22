package backend.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(404, "C_001","유저가 존재하지 않습니다"),
    RESERVATION_NOT_FOUND(404, "C_002","예약이 존재하지 않습니다"),
    BOARD_NOT_FOUND(404, "C_003","작성글이 존재하지 않습니다"),
    CLUB_ROOM_NOT_FOUND(404, "C_004","동아리방이 존재하지 않습니다"),
    COMMENT_NOT_FOUND(404, "C_005","작성글이 존재하지 않습니다"),
    REFRESH_TOKEN_NOT_FOUND(404, "C_006","refresh 토큰이 존재하지 않습니다"),
    DUPLICATED_EMAIL(409, "AU_001", "이미 가입된 Email입니다."),
    BAD_LOGIN(400, "AU_002", "잘못된 아이디 또는 패스워드입니다."),
    UNAUTHORIZED_EMAIL(400, "AU_003", "인증되지 않은 Email입니다."),
    INVALID_CODE(400, "AU_004", "코드가 옳지 못합니다."),
    ALREADY_VERIFIED_EMAIL(400, "AU_005", "이미 인증된 이메일입니다."),
    ALREADY_AUTH_CODE_SENT(400, "AU_005", "이미 인증코드가 전송 되었습니다."),
    NEED_AUTH_CODE_SEND(400, "AU_006", "인증코드를 다시 요청하세요."),
    NEED_LOGIN(401, "AU_007", "로그인이 필요합니다"),
    NOT_LOGIN(401, "AU_008", "로그인이 되어있지 않습니다"),
    UNAUTHORIZED_REDIRECT_URI(400, "AU_009", "인증되지 않은 REDIRECT_URI입니다."),
    FORBIDDEN(403, "AU_010", "다른 사용자의 작성 글을 삭제 혹은 수정할 권한이 없습니다."),
    INVALID_RESERVATION_TIME_REQUEST(400, "BE_001", "예약 시간의 형식이 올바르지 않습니다"),
    ALREADY_RESERVED_TIME(400, "BE_002", "이미 예약된 시간입니다"),
    ALREADY_RESERVATION_EXIST(400, "BE_003", "이미 예약을 보유하고 있습니다"),
    FCM_TOKEN_NOT_FOUND(404, "FCM_001", "FCM token이 존재하지 않습니다"),
    FAILED_FCM_ACCESS_TOKEN_REQUEST(500, "FCM_002", "FCM access token 발급을 실패했습니다"),
    FAILED_FCM_REQUEST(500, "FCM_003", "FCM 통신을 실패했습니다"),
    FAILED_JSON_CONVERT(500, "J_001", "Json 변환에 실패했습니다."),
    FAILED_EMAIL_SEND(400, "E_001", "이메일이 옳바른지 확인해주세요."),
    WRONG_EMAIL_(500, "E_002", "이메일 형식을 확인해주세요."),
    NOTIFICATION_NOT_FOUND(404, "N_001","알림이 존재하지 않습니다");

    private final int status;
    private final String code;
    private final String message;
}
