//package spring.backend.library.util;
//
//import java.util.Arrays;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.apache.commons.lang3.StringUtils;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@ToString
//public class Header {
//
//  private String columnName;
//
//  private String linkColumnName;
//
//  private boolean isKey;
//
//  public static Header parse(String fieldName) {
//    if (StringUtils.isBlank(fieldName)) {
//      return null;
//    }
//
//    fieldName = Arrays.stream(fieldName.split("\\."))
//        .map(Header::correctFieldName).reduce((a, b) -> a.concat(".").concat(b)).orElse(null);
//
//    Header h = new Header();
//    if (fieldName.contains("/k")) {
//      h.setKey(true);
//
//      fieldName = fieldName.replace("/k", "");
//    }
//
//    if (fieldName.contains("[")) {
//      String[] strs = fieldName.replace("]", "").split("\\[");
//      h.setColumnName(strs[0]);
//      h.setLinkColumnName(strs[1]);
//    } else {
//      h.setColumnName(fieldName);
//    }
//
//    return h;
//  }
//
//  private static String correctFieldName(String fieldName) {
//    if (!fieldName.contains("_")) {
//      return fieldName;
//    }
//
//    fieldName = fieldName.trim().toLowerCase();
//    String[] strs = fieldName.split("_");
//
//    int size = strs.length;
//    StringBuilder builder = new StringBuilder(strs[0]);
//    for (int j = 1; j < size; j++) {
//      builder.append(strs[j].substring(0, 1).toUpperCase()).append(strs[j].substring(1));
//    }
//
//    return builder.toString();
//  }
//}
