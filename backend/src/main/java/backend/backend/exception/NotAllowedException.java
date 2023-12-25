package backend.backend.exception;

public class NotAllowedException extends BusinessException {
    public NotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}