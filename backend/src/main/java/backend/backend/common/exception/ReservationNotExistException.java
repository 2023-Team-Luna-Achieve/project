package backend.backend.common.exception;

public class ReservationNotExistException extends BusinessException{
    public ReservationNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
