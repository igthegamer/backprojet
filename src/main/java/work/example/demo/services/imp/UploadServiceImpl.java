package work.example.demo.services.imp;

import work.example.demo.services.UploadService;
import work.example.demo.utilities.CloudManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public String uploadImage(MultipartFile fileInput) throws IOException {
        String message = "";
        String image ;
        File file = multipartToFile(fileInput);
        try {
            image = CloudManager.uploadImage(file);
        } catch (Exception e) {
            throw new RuntimeException("Upload problem");
        }
        finally {
            file.delete();
        }
        return image;
    }
    static File multipartToFile(MultipartFile image) throws IllegalStateException, IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("." + fileName);
        try {
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.toFile();
    }
}
