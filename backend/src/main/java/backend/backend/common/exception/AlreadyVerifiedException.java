package backend.backend.common.exception;

public class AlreadyVerifiedException extends BusinessException{
    public AlreadyVerifiedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
