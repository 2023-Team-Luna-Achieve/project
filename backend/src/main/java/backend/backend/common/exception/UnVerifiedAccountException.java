package backend.backend.common.exception;

public class UnVerifiedAccountException extends BusinessException{
    public UnVerifiedAccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
