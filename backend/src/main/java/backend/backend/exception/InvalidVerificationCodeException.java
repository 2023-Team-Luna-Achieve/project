package backend.backend.exception;

public class InvalidVerificationCodeException extends BusinessException{
    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
