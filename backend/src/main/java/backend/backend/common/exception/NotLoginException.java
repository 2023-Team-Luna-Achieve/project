package backend.backend.common.exception;

public class NotLoginException extends BusinessException {
    public NotLoginException(ErrorCode errorCode) {
        super(errorCode);
    }
}
