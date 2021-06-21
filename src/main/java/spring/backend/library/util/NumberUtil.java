package spring.backend.library.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil {

  private static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);

  public static boolean isCreatable(String source) {
    return NumberUtils.isCreatable(source);
  }

  public static String format(Number source, String pattern) {
    DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(DateUtil.getLocaleDefault());
    format.setRoundingMode(RoundingMode.HALF_UP);
    format.applyPattern(pattern);

    return source == null ? null : format.format(source);
  }

  public static Double toDouble(Number source) {
    return source == null ? null : source.doubleValue();
  }

  public static Long toLong(Number source) {
    return source == null ? null : source.longValue();
  }

  public static Integer toInteger(Number source) {
    return source == null ? null : source.intValue();
  }

  public static Short toShort(Number source) {
    return source == null ? null : source.shortValue();
  }

  public static Byte toByte(Number source) {
    return source == null ? null : source.byteValue();
  }

  public static int toInteger(long value) {
    if ((int) value != value) {
      return Integer.MAX_VALUE;
    }
    return (int) value;
  }

  public static Long toLong(Object source) {
    if (source == null) {
      return null;
    }

    if (source instanceof Number) {
      return toLong((Number) source);
    }

    if (source instanceof String) {
      String str = (String) source;
      if (NumberUtils.isCreatable(str)) {
        return NumberUtils.toLong(str);
      }
    }

    return null;
  }

  public static Integer toInteger(Object source) {
    if (source == null) {
      return null;
    }

    if (source instanceof Number) {
      return toInteger((Number) source);
    }

    if (source instanceof String) {
      String str = (String) source;
      if (NumberUtils.isCreatable(str)) {
        return NumberUtils.toInt(str);
      }
    }

    return null;
  }

  public static Short toShort(Object source) {
    if (source == null) {
      return null;
    }

    if (source instanceof Number) {
      return toShort((Number) source);
    }

    if (source instanceof String) {
      String str = (String) source;
      if (NumberUtils.isCreatable(str)) {
        return NumberUtils.toShort(str);
      }
    }

    return null;
  }

  public static Byte toByte(Object source) {
    if (source == null) {
      return null;
    }

    if (source instanceof Number) {
      return toByte((Number) source);
    }

    if (source instanceof String) {
      String str = (String) source;
      if (NumberUtils.isCreatable(str)) {
        return NumberUtils.toByte(str);
      }
    }

    return null;
  }

  public static Double toDouble(Object source) {
    if (source == null) {
      return null;
    }

    if (source instanceof Number) {
      return toDouble((Number) source);
    }

    if (source instanceof String) {
      String str = (String) source;
      if (NumberUtils.isCreatable(str)) {
        return NumberUtils.toDouble(str);
      }
    }

    return null;
  }

  public static List<Long> toLongList(List<?> source) {
    if (source == null) {
      return null;
    }
    return source.stream().map(NumberUtil::toLong).collect(Collectors.toList());
  }

  public static Long[] toLongArray(Object[] source) {
    if (source == null) {
      return null;
    }
    return Arrays.stream(source).map(NumberUtil::toLong).toArray(Long[]::new);
  }

  public static List<Long> toLongList(Object[] source) {
    if (source == null) {
      return null;
    }
    return Arrays.stream(source).map(NumberUtil::toLong).collect(Collectors.toList());
  }

  public static Set<Long> toLongSet(Object[] source) {
    if (source == null) {
      return null;
    }
    return Arrays.stream(source).map(NumberUtil::toLong).collect(Collectors.toSet());
  }

  public static Integer[] toIntegerArray(Object[] source) {
    if (source == null) {
      return null;
    }
    return Arrays.stream(source).map(NumberUtil::toInteger).toArray(Integer[]::new);
  }

  public static List<Integer> toIntegerList(Object[] source) {
    if (source == null) {
      return null;
    }
    return Arrays.stream(source).map(NumberUtil::toInteger).collect(Collectors.toList());
  }

  public static Set<Integer> toIntegerSet(Object[] source) {
    if (source == null) {
      return null;
    }
    return Arrays.stream(source).map(NumberUtil::toInteger).collect(Collectors.toSet());
  }

  public static Integer[] toIntegerArray(String source) {
    if (source == null) {
      return null;
    }
    return toIntegerArray(source.split(","));
  }

  public static List<Integer> toIntegerList(String source) {
    if (source == null) {
      return null;
    }
    return toIntegerList(source.split(","));
  }

  public static Set<Integer> toIntegerSet(String source) {
    if (source == null) {
      return null;
    }
    return toIntegerSet(source.split(","));
  }

  public static Long[] toLongArray(String source) {
    if (source == null) {
      return null;
    }
    return toLongArray(source.split(","));
  }

  public static List<Long> toLongList(String source) {
    if (source == null) {
      return null;
    }
    return toLongList(source.split(","));
  }

  public static Set<Long> toLongSet(String source) {
    if (source == null) {
      return null;
    }
    return toLongSet(source.split(","));
  }

  public static String toString(final Number... source) {
    if (source == null || source.length == 0) {
      return null;
    }
    return Arrays
        .stream(source)
        .map(String::valueOf)
        .reduce((a, b) -> a.concat(",").concat(b))
        .get();
  }

  public static double round(double x, int scale) {
    return Precision.round(x, scale);
  }

  public static float round(float x, int scale) {
    return Precision.round(x, scale);
  }

  public static String num2String(Long num) {
    if (num == null) {
      return null;
    }

    if (num == 0L) {
      return "không";
    }

    if (num < 0L) {
      return "âm " + num2String(-num);
    }

    String str = Long.valueOf(num).toString();

    // zero padding in front of string to prepare for splitting
    switch (str.length() % 3) {
      case 1:
        str = "00" + str;
        break;
      case 2:
        str = "0" + str;
        break;
      default:
        break;
    }

    // Split into chunks of 3 digits each
    List<String> groupOfThousand = Arrays.asList(str.split("(?<=\\G.{3})"));

    boolean showZeroHundred = doShowZeroHundred(groupOfThousand);

    AtomicInteger index = new AtomicInteger();
    String result = groupOfThousand.stream()
        .map(triplet -> readTriple(triplet, showZeroHundred && index.get() > 0))
        .map(vnString -> vnString.trim().isEmpty()
            ? ""
            : vnString + " " + thousandsName.get(groupOfThousand.size() - 1 - index.get())
        )
        .peek(s -> index.getAndIncrement())
        .collect(Collectors.joining(" "))
        .replaceAll("\\s+", " ")
        .trim();

    return result.substring(0, 1).toUpperCase() + result.substring(1);
  }

  private static boolean doShowZeroHundred(List<String> groupOfThousand) {
    int count = 0;
    int i = groupOfThousand.size() - 1;
    while (i >= 0 && groupOfThousand.get(i).equals("000")) {
      count++;
      i--;
    }

    return count < groupOfThousand.size() - 1;
  }

  // Static
  private static final List<String> digitsName = Arrays.asList(
      "không",
      "một",
      "hai",
      "ba",
      "bốn",
      "năm",
      "sáu",
      "bảy",
      "tám",
      "chín"
  );

  private static final List<String> thousandsName = Arrays.asList(
      "",
      "nghìn",
      "triệu",
      "tỷ",
      "nghìn tỷ",
      "triệu tỷ",
      "tỷ tỷ"
  );

  private static String readTriple(String triplet, boolean showZeroHundred) {
    List<Integer> digitList = stringToInt(triplet);

    int a = digitList.get(0);
    int b = digitList.get(1);
    int c = digitList.get(2);

    if (a == 0) {
      if (b == 0 && c == 0) {
        return "";
      }

      if (showZeroHundred) {
        return "không trăm " + readPair(b, c);
      }

      if (b == 0) {
        return digitsName.get(c);
      } else {
        return readPair(b, c);
      }

    }

    return digitsName.get(a) + " trăm " + readPair(b, c);
  }


  private static String readPair(int b, int c) {
    String temp;

    switch (b) {
      case 0:
        return c == 0 ? "" : "lẻ " + digitsName.get(c);
      case 1:
        switch (c) {
          case 0:
            temp = " ";
            break;
          case 5:
            temp = "lăm ";
            break;
          default:
            temp = digitsName.get(c);
            break;
        }

        return "mười " + temp;
      default:
        switch (c) {
          case 0:
            temp = "";
            break;
          case 1:
            temp = "mốt ";
            break;
          case 4:
            temp = "tư ";
            break;
          case 5:
            temp = "lăm ";
            break;
          default:
            temp = digitsName.get(c);
            break;
        }

        return digitsName.get(b) + " mươi " + temp;
    }
  }

  private static List<Integer> stringToInt(String triplet) {
    return triplet.chars()
        .map(NumberUtil::charToInt)
        .boxed()
        .collect(Collectors.toList());
  }

  private static int charToInt(int c) {
    return c - '0';
  }
}