package backend.backend.common.exception;

public class EmailAuthCodeSendException extends BusinessException{
    public EmailAuthCodeSendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
