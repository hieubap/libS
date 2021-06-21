//package spring.backend.library.util;
//
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import org.apache.logging.log4j.util.Base64Util;
//import org.apache.poi.util.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.TripleDesException.DecryptError;
//import vn.isofh.common.exception.TripleDesException.EncryptError;
//
//public class TripleDesUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(TripleDesUtil.class);
//
//  public static String decrypt3Des(String key, String data) {
//    if (StringUtil.isBlank(key)) {
//      return null;
//    }
//
//    return decrypt3Des(key.getBytes(), data);
//  }
//
//  public static String decrypt3Des(byte[] keyData, String data) {
//    if (StringUtil.isBlank(data)) {
//      return null;
//    }
//
//    try {
//      data = data.toUpperCase();
//      SecretKey key = new SecretKeySpec(keyData, "DESede");
//      Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
//      IvParameterSpec ivSpec = new IvParameterSpec(new byte[8]);
//      cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
//      String _data = new String(DatatypeConverter.parseHexBinary(data), StandardCharsets.UTF_8);
//      byte[] raw = Base64Util.decodeBase64(_data);
//      byte[] stringBytes = cipher.doFinal(raw);
//      return new String(stringBytes, StandardCharsets.UTF_8);
//    } catch (NoSuchAlgorithmException | NoSuchPaddingException
//        | InvalidKeyException | InvalidAlgorithmParameterException
//        | IllegalBlockSizeException | BadPaddingException e) {
//      throw new DecryptError(e);
//    }
//  }
//
//  public static String encrypt3Des(String key, String data) {
//    if (StringUtil.isBlank(key)) {
//      return null;
//    }
//
//    return encrypt3Des(key.getBytes(), data);
//  }
//
//  public static String encrypt3Des(byte[] keyData, String data) {
//    if (StringUtil.isBlank(data)) {
//      return null;
//    }
//
//    try {
//      SecretKey key = new SecretKeySpec(keyData, "DESede");
//      Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
//
//      IvParameterSpec ivSpec = new IvParameterSpec(new byte[8]);
//      cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//      byte[] stringBytes = data.getBytes(StandardCharsets.UTF_8);
//      byte[] cipherText = cipher.doFinal(stringBytes);
//
//      String cipher_msg = Base64Util.encodeBase64String(cipherText);
//      cipher_msg = DatatypeConverter.printHexBinary(cipher_msg.getBytes());
//
//      return cipher_msg.toLowerCase();
//    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
//        | InvalidAlgorithmParameterException | IllegalBlockSizeException
//        | BadPaddingException e) {
//      throw new EncryptError(e);
//    }
//  }
//}
