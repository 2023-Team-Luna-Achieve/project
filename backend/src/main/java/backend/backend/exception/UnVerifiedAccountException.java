package backend.backend.exception;

public class UnVerifiedAccountException extends BusinessException{
    public UnVerifiedAccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
