package backend.backend.exception.globalcontrolleradvice;

import backend.backend.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response =
                ErrorResponse.builder()
                        .errorMessage(errorCode.getMessage())
                        .businessCode(errorCode.getCode())
                        .build();
        log.warn(e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
//
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ExceptionMessage notFoundException(NotFoundException e) {
//        log.info("NotFoundException: {}", e.getMessage(), e);
//        return new ExceptionMessage(e.getMessage());
//    }
//
//    @ExceptionHandler(InvalidVerificationCodeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionMessage invalidVerificationCodeException(InvalidVerificationCodeException e) {
//        log.info("InvalidVerificationCodeException: {}", e.getMessage(), e);
//        return new ExceptionMessage(e.getMessage());
//    }
//
//    @ExceptionHandler(InvalidValueException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionMessage invalidValueException(InvalidValueException e) {
//        log.info("InvalidValueException: {}", e.getMessage(), e);
//        return new ExceptionMessage(e.getMessage());
//    }
//
//
//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionMessage authenticationException(AuthenticationException e) {
//        log.info("AuthenticationException: {}", e.getMessage(), e);
//        return new ExceptionMessage(e.getMessage());
//    }
//
//
//    @ExceptionHandler(UnVerifiedAccountException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionMessage unverifiedAccountException(UnVerifiedAccountException e) {
//        log.info("UnVerifiedAccountException: {}", e.getMessage(), e);
//        return new ExceptionMessage(e.getMessage());
//    }
//
//    @ExceptionHandler(EmailAlreadyInUseException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ExceptionMessage emailAlreadyInUseException(EmailAlreadyInUseException e) {
//        log.info("EmailAlreadyInUseException: {}", e.getMessage(), e);
//        return new ExceptionMessage(e.getMessage());
//    }
}
