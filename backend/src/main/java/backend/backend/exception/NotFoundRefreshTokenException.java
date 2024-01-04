package backend.backend.exception;

public class NotFoundRefreshTokenException extends BusinessException {
    public NotFoundRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
