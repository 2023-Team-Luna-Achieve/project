package backend.backend.common.exception;

public class InvalidReservationTimeException extends BusinessException {
    public InvalidReservationTimeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
