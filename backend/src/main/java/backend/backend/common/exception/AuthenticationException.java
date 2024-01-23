package backend.backend.common.exception;

public class AuthenticationException extends BusinessException{
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
