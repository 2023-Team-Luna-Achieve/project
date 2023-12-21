package backend.backend.exception;

public class NotLoginException extends BusinessException {
    public NotLoginException(ErrorCode errorCode) {
        super(errorCode);
    }
}
