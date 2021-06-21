//package spring.backend.library.util;
//
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.logging.log4j.util.Base64Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.CryptException;
//
//public class AesUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(AesUtil.class);
//
//  private static final String AES = "AES/CBC/PKCS5Padding";
//
//  public static SecretKeySpec getSymmetricKey(String pass) {
//    return getSymmetricKey(pass.getBytes());
//  }
//
//  public static SecretKeySpec getSymmetricKey(byte[] pass) {
//    return new SecretKeySpec(pass, "AES");
//  }
//
//  public static byte[] encrypt(byte[] data, SecretKeySpec key) {
//    try {
//      Cipher c = Cipher.getInstance(AES);
//
//      byte[] iv = new byte[c.getBlockSize()];
//      new SecureRandom().nextBytes(iv);
//      IvParameterSpec ivParams = new IvParameterSpec(iv);
//      c.init(Cipher.ENCRYPT_MODE, key, ivParams);
//
//      return ArrayUtils.addAll(iv, c.doFinal(data));
//    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
//        | BadPaddingException | InvalidKeyException e) {
//      throw new CryptException.InvalidPrivateKey(e);
//    } catch (IllegalBlockSizeException e) {
//      throw new CryptException.InvalidData(e);
//    }
//  }
//
//  public static byte[] encrypt(byte[] data, String pass) {
//    return encrypt(data, getSymmetricKey(pass));
//  }
//
//  public static byte[] decrypt(byte[] data, SecretKeySpec key) {
//    try {
//      Cipher c = Cipher.getInstance(AES);
//      byte[] iv = new byte[c.getBlockSize()];
//      byte[] d = new byte[data.length - iv.length];
//
//      System.arraycopy(data, 0, iv, 0, iv.length);
//      System.arraycopy(data, iv.length, d, 0, d.length);
//
//      c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
//      return c.doFinal(d);
//    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
//        | BadPaddingException | InvalidKeyException e) {
//      throw new CryptException.InvalidPrivateKey(e);
//    } catch (IllegalBlockSizeException e) {
//      throw new CryptException.InvalidData(e);
//    }
//  }
//
//  public static String decrypt(String data, String pass) {
//    return new String(decrypt(Base64Util.decodeBase64(data), getSymmetricKey(pass)));
//  }
//
//  public static byte[] decrypt(byte[] data, String pass) {
//    return decrypt(data, getSymmetricKey(pass));
//  }
//}
