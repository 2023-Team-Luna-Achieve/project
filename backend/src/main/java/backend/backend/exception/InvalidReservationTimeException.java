package backend.backend.exception;

import backend.backend.exception.BusinessException;

public class InvalidReservationTimeException extends BusinessException {
    public InvalidReservationTimeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
