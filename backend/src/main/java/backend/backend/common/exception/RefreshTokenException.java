package backend.backend.common.exception;

public class RefreshTokenException extends BusinessException{
    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
