package backend.backend.exception;

public class InvalidVerificationCodeException extends BusinessException{
    public InvalidVerificationCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
