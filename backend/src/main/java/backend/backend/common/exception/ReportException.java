package backend.backend.common.exception;

public class ReportException extends BusinessException {
    public ReportException(ErrorCode errorCode) {
        super(errorCode);
    }
}
