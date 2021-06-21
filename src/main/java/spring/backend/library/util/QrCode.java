//package spring.backend.library.util;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.BinaryBitmap;
//import com.google.zxing.DecodeHintType;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatReader;
//import com.google.zxing.NotFoundException;
//import com.google.zxing.Result;
//import com.google.zxing.Writer;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.common.HybridBinarizer;
//import com.google.zxing.oned.Code128Writer;
//import com.google.zxing.qrcode.QRCodeWriter;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.QrException;
//
//public class QrCode {
//
//  private final static Logger logger = LoggerFactory.getLogger(QrCode.class);
//
//  public static void generateQr(Path path, String data, Integer width, Integer height) {
//    generate(path, data, BarcodeFormat.QR_CODE, width, height);
//  }
//
//  public static void generateBarcode(Path path, String data, Integer width, Integer height) {
//    generate(path, data, BarcodeFormat.CODE_128, width, height);
//  }
//
//  public static void generateQr(Path path, String data) {
//    generateQr(path, data, null, null);
//  }
//
//  public static void generateBarcode(Path path, String data) {
//    generateBarcode(path, data, null, null);
//  }
//
//  private static void generate(Path path, String data, BarcodeFormat barcodeFormat, Integer width, Integer height) {
//    try {
//      logger.debug("Generate a {}: name {}", barcodeFormat, path.getFileName().toString());
//
//      if (!"png".equals(FileUtil.getExtension(path))) {
//        throw new QrException.InvalidBarcodeFormat(barcodeFormat + "");
//      }
//
//      Writer qrCodeWriter = barcodeFormat.equals(BarcodeFormat.CODE_128) ? new Code128Writer() :
//          barcodeFormat.equals(BarcodeFormat.QR_CODE) ? new QRCodeWriter() : null;
//
//      if (qrCodeWriter == null) {
//        throw new QrException.InvalidBarcodeFormat(barcodeFormat + "");
//      }
//
//      Map<EncodeHintType, Object> hints = new HashMap<>();
//      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//      hints.put(EncodeHintType.MARGIN, 1);
//
//      width = width == null ? 400 : width;
//      height = height == null ? 400 : height;
//
//      BitMatrix matrix = qrCodeWriter.encode(data, barcodeFormat, width, height, hints);
//      MatrixToImageWriter.writeToPath(matrix, "png", path);
//    } catch (WriterException | IOException e) {
//      throw new QrException(e);
//    }
//  }
//
//  public static String readQr(Path path) {
//    try {
//
//      Map<DecodeHintType, Object> hints = new HashMap<>();
//      hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
//      hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_128));
////      hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
//      hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//
//      BinaryBitmap binaryBitmap = new BinaryBitmap(
//          new HybridBinarizer(new BufferedImageLuminanceSource(ImageUtil.getBufferedImage(path))));
//
//      Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hints);
//      return qrCodeResult.getText();
//    } catch (NotFoundException e) {
//      throw new QrException(e);
//    }
//  }
//}
