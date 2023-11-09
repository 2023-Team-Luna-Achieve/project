package backend.backend.exception;

public class AlreadyVerifiedException extends BusinessException{
    public AlreadyVerifiedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
