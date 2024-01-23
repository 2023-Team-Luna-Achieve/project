package backend.backend.common.exception;

public class InvalidVerificationCodeException extends BusinessException{
    public InvalidVerificationCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
