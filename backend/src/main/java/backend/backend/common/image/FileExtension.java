package backend.backend.common.image;

import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.FileExtensionException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FileExtension {
    JPEG(".JPEG"),
    JPG(".JPG"),
    JFIF(".JFIF"),
    PNG(".PNG"),
    SVG(".SVG");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    static FileExtension from(String fileName) {
        return Arrays.stream(values())
                .filter(fileExtension -> fileName.toUpperCase().endsWith(fileExtension.getExtension()))
                .findFirst()
                .orElseThrow(() -> new FileExtensionException(ErrorCode.INVALID_FILE_EXTENSION));
    }
}
