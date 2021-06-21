//package spring.backend.library.util;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Path;
//import java.util.regex.Pattern;
//import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.StorageException;
//
//public class Base64Util {
//
//  private final static Logger logger = LoggerFactory.getLogger(Base64Util.class);
//
//  public static String encodeFileToBase64Binary(Path path) {
//    byte[] encoded = Base64.encodeBase64(FileUtil.readFileToByteArray(path));
//    return new String(encoded, StandardCharsets.UTF_8);
//  }
//
//  public static String encodeFileToBase64Binary(File file) {
//    byte[] encoded = Base64.encodeBase64(FileUtil.readFileToByteArray(file));
//    return new String(encoded, StandardCharsets.UTF_8);
//  }
//
//  public static Path decodeBase64BinaryToFile(String fileBase64, Path path) {
//    byte[] decoded = Base64.decodeBase64(fileBase64);
//    FileOutputStream fos = null;
//    try {
//      fos = new FileOutputStream(path.toFile());
//      fos.write(decoded);
//
//      return path;
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(path.getFileName().toString(), e);
//    } finally {
//      if (fos != null) {
//        try {
//          fos.close();
//        } catch (IOException e) {
//          logger.error(e.getMessage(), e);
//          throw new StorageException.ReadFileException(path.getFileName().toString(), e);
//        }
//      }
//    }
//  }
//
//  public static byte[] decodeBase64(String base64) {
//    return Base64.decodeBase64(base64);
//  }
//
//  public static String encodeBase64String(byte[] binaryData) {
//    return Base64.encodeBase64String(binaryData);
//  }
//
//  public static boolean isBase64Encode(String base64) {
//    return Pattern.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$", base64);
//  }
//}
