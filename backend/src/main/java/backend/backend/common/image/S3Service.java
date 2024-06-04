package backend.backend.common.image;

import backend.backend.common.exception.ErrorCode;
import backend.backend.common.exception.UploadFailException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;


    public String upload(MultipartFile file) {
        String imageUrl = "";
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket , fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new UploadFailException(ErrorCode.FAILED_IMAGE_UPLOAD);
        }

        return imageUrl;
    }

    private String createFileName(String fileName) {
        FileExtension extension = FileExtension.from(fileName);
        return UUID.randomUUID().toString().concat(extension.getExtension());
    }
}
