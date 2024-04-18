package backend.backend.common.exception;

public class AlreadyReservationExistException extends BusinessException{
    public AlreadyReservationExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
