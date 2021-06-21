//package spring.backend.library.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.time.ZonedDateTime;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FilenameUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.StorageException;
//
//public class FileUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
//
//  public static String readFileToString(Path path) {
//    return readFileToString(path.toFile());
//  }
//
//  public static String readFileToString(File file) {
//    try {
//      if (!file.exists()) {
//        throw new StorageException.ReadFileException(file.getAbsolutePath());
//      }
//
//      return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(file.getAbsolutePath(), e);
//    }
//  }
//
//  public static void copyFile(Path srcPath, Path destPath) {
//    try {
//      FileUtils.copyFile(srcPath.toFile(), destPath.toFile());
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(srcPath.toString(), e);
//    }
//  }
//
//  public static FileInputStream openInputStream(Path path) {
//    return openInputStream(path.toFile());
//  }
//
//  public static FileInputStream openInputStream(File file) {
//    try {
//      return FileUtils.openInputStream(file);
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(file.getAbsolutePath(), e);
//    }
//  }
//
//  public static FileOutputStream openOutputStream(Path path) {
//    return openOutputStream(path.toFile());
//  }
//
//  public static FileOutputStream openOutputStream(File file) {
//    try {
//      return FileUtils.openOutputStream(file);
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(file.getAbsolutePath(), e);
//    }
//  }
//
//  public static byte[] readFileToByteArray(Path path) {
//    return readFileToByteArray(path.toFile());
//  }
//
//  public static byte[] readFileToByteArray(File file) {
//    try {
//      if (!file.exists()) {
//        throw new StorageException.ReadFileException(file.toString());
//      }
//
//      return FileUtils.readFileToByteArray(file);
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(file.toString(), e);
//    }
//  }
//
//  public static void createDirectories(Path path) {
//    File file = path.toFile();
//
//    if (file.isFile()) {
//      path = path.getParent();
//      file = path.toFile();
//    }
//
//    if (!file.exists() || !file.isDirectory()) {
//      try {
//        Files.createDirectories(path);
//      } catch (IOException e) {
//        logger.error(e.getMessage(), e);
//        throw new StorageException.FailedToCreateDirectory(path.toString(), e);
//      }
//    }
//  }
//
//  public static String removeExtension(String path) {
//    if (path == null) {
//      return null;
//    }
//
//    return FilenameUtils.removeExtension(path);
//  }
//
//  public static Path replaceExtension(Path path, String extension) {
//    if (path == null) {
//      return null;
//    }
//
//    return Paths.get(FilenameUtils.removeExtension(path.toString()) + "." + extension);
//  }
//
//  public static Path addSuffix(Path path, String suffix) {
//    if (path == null) {
//      return null;
//    }
//
//    Path parent = path.toAbsolutePath().getParent();
//    String fileName = path.getFileName().toString();
//    return parent.resolve(removeExtension(fileName) + suffix + "." + getExtension(fileName));
//  }
//
//  public static String getExtension(Path path) {
//    if (path == null) {
//      return null;
//    }
//
//    return FilenameUtils.getExtension(path.getFileName().toString()).toLowerCase();
//  }
//
//  public static String getExtension(String fileName) {
//    if (fileName == null) {
//      return null;
//    }
//
//    return FilenameUtils.getExtension(fileName).toLowerCase();
//  }
//
//  public static String getBaseName(Path path) {
//    if (path == null) {
//      return null;
//    }
//
//    return FilenameUtils.getBaseName(path.getFileName().toString());
//  }
//
//  public static String getFileName(String url) {
//    if (url == null) {
//      return null;
//    }
//
//    try {
//      return FilenameUtils.getName(new URL(url).getPath()).toLowerCase();
//    } catch (MalformedURLException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(url, e);
//    }
//  }
//
//  public static void forceDelete(Path file) {
//    try {
//      FileUtils.forceDelete(file.toFile());
//      logger.info("Delete successful: " + file.toString());
//    } catch (IOException e) {
//      logger.error("Delete fail: " + file.toString());
//      throw new StorageException.ReadFileException(file.toString(), e);
//    }
//  }
//
//  public static boolean isFileOlder(Path file, LocalDate date) {
//    return isFileOlder(file.toFile(), date);
//  }
//
//  public static boolean isFileOlder(File file, LocalDate date) {
//    if (file == null || date == null) {
//      return false;
//    }
//
//    return FileUtils.isFileOlder(file, DateUtil.getDate(date));
//  }
//
//  public static boolean isFileOlder(Path file, ZonedDateTime date) {
//    return isFileOlder(file.toFile(), date);
//  }
//
//  public static boolean isFileOlder(File file, ZonedDateTime date) {
//    if (file == null || date == null) {
//      return false;
//    }
//
//    return FileUtils.isFileOlder(file, DateUtil.getDate(date));
//  }
//}