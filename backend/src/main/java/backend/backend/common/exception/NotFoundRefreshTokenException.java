package backend.backend.common.exception;

public class NotFoundRefreshTokenException extends BusinessException {
    public NotFoundRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
