package spring.backend.library.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  public void init();

  public void save(MultipartFile file);

  public void deleteAll();
}