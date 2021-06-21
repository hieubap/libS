//package spring.backend.library.util;
//
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Hex;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.ShaException;
//
//public class DigestUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(DigestUtil.class);
//
//  public static String sign(String value, String key) {
//    try {
//      Mac sha256HMAC = Mac.getInstance("HmacSHA256");
//      SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
//
//      sha256HMAC.init(secretKey);
//      byte[] macData = sha256HMAC.doFinal(value.getBytes());
//
//      return Hex.encodeHexString(macData);
//    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
//      throw new ShaException.InvalidKey(e);
//    }
//  }
//
//  public static String md5Hex(String value) {
//    return DigestUtils.md5Hex(value);
//  }
//
//  public static String sha256Hex(String value) {
//    return DigestUtils.sha256Hex(value);
//  }
//
//  public static String sha1Hex(String value) {
//    return DigestUtils.sha1Hex(value);
//  }
//
//  public static byte[] sha1(String value) {
//    return DigestUtils.sha1(value);
//  }
//}
