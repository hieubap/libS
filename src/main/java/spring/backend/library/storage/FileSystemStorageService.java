package spring.backend.library.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

  @Value("{file.upload-dir}")
  private String dirUpload;

  private final Path root = Paths.get(dirUpload);

  @Override
  public void init() {
    try {
      Files.createDirectory(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file) {
    try {
      if(!Files.exists(root)){
        init();
      }
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }


  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }


}