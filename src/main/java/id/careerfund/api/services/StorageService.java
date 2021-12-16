package id.careerfund.api.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadFile(MultipartFile multipartFile, String path);

    String deleteFile(String filename);
}
