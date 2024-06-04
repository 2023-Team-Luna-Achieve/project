package backend.backend.common.image;

import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.FileExtensionException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FileExtension {
    JPEG(".jpeg"),
    JPG(".jpg"),
    JFIF(".jfif"),
    PNG(".png"),
    SVG(".svg");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    static FileExtension from(String fileName) {
        return Arrays.stream(values())
                .filter(fileExtension -> fileName.endsWith(fileExtension.getExtension()))
                .findFirst()
                .orElseThrow(() -> new FileExtensionException(ErrorCode.INVALID_FILE_EXTENSION));
    }
}
