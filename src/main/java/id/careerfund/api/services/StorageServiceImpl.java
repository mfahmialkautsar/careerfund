package id.careerfund.api.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {
    private final AmazonS3 s3;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.bucket.url}")
    private String bucketUrl;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        File file = covertMultipartFileToFile(multipartFile);
        String fileName = String.format("/images/profile/%s_%s", System.currentTimeMillis(), multipartFile.getOriginalFilename());
        s3.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return String.format("%s/%s", bucketUrl, fileName);
    }

    @Override
    public String deleteFile(String filename) {
        s3.deleteObject(bucketName, filename);
        return "Removed";
    }

    private File covertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(Optional.ofNullable(file.getOriginalFilename()).orElse("unnamed"));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
