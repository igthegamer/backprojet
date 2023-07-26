package work.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    String uploadImage(MultipartFile fileInput) throws IOException;



}

