//package spring.backend.library.util;
//
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import org.apache.logging.log4j.util.Base64Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.CryptException;
//
//public class RsaUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(RsaUtil.class);
//
//  /*
//  Hướng dẫn tạo private, public key:
//  1. openssl genrsa -out private.pem 1024
//  2. openssl rsa -in private.pem -pubout -outform PEM -out public_key.pem
//  3. openssl pkcs8 -topk8 -inform PEM -in private.pem -out private_key.pem -nocrypt
//   */
//
//  private static final String RSA = "RSA";
//
//  public static PublicKey getPublicKey(String content) {
//    try {
//      byte[] byteKey = Base64Util.decodeBase64(content);
//      X509EncodedKeySpec x509publicKey = new X509EncodedKeySpec(byteKey);
//      KeyFactory kf = KeyFactory.getInstance(RSA);
//      return kf.generatePublic(x509publicKey);
//    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//      throw new CryptException.InvalidPublicKey(e);
//    }
//  }
//
//  public static PrivateKey getPrivateKey(String content) {
//    try {
//      byte[] byteKey = Base64Util.decodeBase64(content);
//      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byteKey);
//      KeyFactory keyFactory = KeyFactory.getInstance(RSA);
//
//      return keyFactory.generatePrivate(keySpec);
//    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//      throw new CryptException.InvalidPrivateKey(e);
//    }
//  }
//
//  public static byte[] encrypt(byte[] data, Key key) {
//    try {
//      logger.debug("Encrypt input: {}", data);
//      Cipher c = Cipher.getInstance(RSA);
//      c.init(Cipher.ENCRYPT_MODE, key);
//      return c.doFinal(data);
//    } catch (NoSuchAlgorithmException | NoSuchPaddingException
//        | IllegalBlockSizeException | InvalidKeyException e) {
//      throw new CryptException.InvalidPrivateKey(e);
//    } catch (BadPaddingException e) {
//      throw new CryptException.InvalidData(e);
//    }
//  }
//
//  public static String encrypt(String data, String publicKey) {
//    return Base64Util.encodeBase64String(encrypt(data.getBytes(), getPublicKey(publicKey)));
//  }
//
//  public static byte[] encrypt(byte[] data, String publicKey) {
//    return encrypt(data, getPublicKey(publicKey));
//  }
//
//  public static byte[] decrypt(byte[] data, Key key) {
//    try {
//      Cipher c = Cipher.getInstance(RSA);
//      c.init(Cipher.DECRYPT_MODE, key);
//      return c.doFinal(data);
//    } catch (NoSuchAlgorithmException | NoSuchPaddingException
//        | IllegalBlockSizeException | InvalidKeyException e) {
//      throw new CryptException.InvalidPrivateKey(e);
//    } catch (BadPaddingException e) {
//      throw new CryptException.InvalidData(e);
//    }
//  }
//
//  public static String decrypt(String data, String privateKey) {
//    return new String(decrypt(Base64Util.decodeBase64(data), getPrivateKey(privateKey)));
//  }
//
//  public static byte[] decrypt(byte[] data, String privateKey) {
//    return decrypt(data, getPrivateKey(privateKey));
//  }
//}
