package backend.backend.common.exception;

public class FileExtensionException extends BusinessException{
    public FileExtensionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
