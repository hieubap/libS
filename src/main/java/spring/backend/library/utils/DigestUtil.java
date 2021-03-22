package spring.backend.library.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtil {
  public static String sha256Hex(String value) {

    String str = DigestUtils.sha256Hex(value);

    return str;
  }
}
