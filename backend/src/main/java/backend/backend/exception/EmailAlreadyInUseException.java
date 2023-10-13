package backend.backend.exception;

public class EmailAlreadyInUseException extends BusinessException{
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
