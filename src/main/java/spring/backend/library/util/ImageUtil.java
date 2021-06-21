//package spring.backend.library.util;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import javax.imageio.ImageIO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import vn.isofh.common.exception.StorageException;
//
//public class ImageUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
//
//  public static BufferedImage getBufferedImage(Path path) {
//    return getBufferedImage(path.toFile());
//  }
//
//  public static BufferedImage getBufferedImage(File file) {
//    try {
//      return ImageIO.read(FileUtil.openInputStream(file));
//    } catch (IOException e) {
//      logger.error(e.getMessage(), e);
//      throw new StorageException.ReadFileException(file.getAbsolutePath(), e);
//    }
//  }
//
//  public static byte[] removeBackground(byte[] imageData) {
//    try {
//      ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
//      BufferedImage initImage = ImageIO.read(bais);
//
//      int width = initImage.getWidth(null);
//      int height = initImage.getHeight(null);
//
//      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//      Graphics g = image.getGraphics();
//      g.drawImage(initImage, 0, 0, null);
//
//      for (int y = 0; y < height; y++) {
//        for (int x = 0; x < width; x++) {
//          int pixel = image.getRGB(x, y);
//          Color color = new Color(pixel);
//
//          Color backColor = new Color(255, 255, 255);
//          int dr = Math.abs(color.getRed() - backColor.getRed());
//          int dg = Math.abs(color.getGreen() - backColor.getGreen());
//          int db = Math.abs(color.getBlue() - backColor.getBlue());
//
//          if (dr < 35 && dg < 35 && db < 35) {
//            image.setRGB(x, y, 0);
//          }
//        }
//      }
//
//      ByteArrayOutputStream baos = new ByteArrayOutputStream();
//      ImageIO.write(image, "png", baos);
//      baos.flush();
//
//      return baos.toByteArray();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    return null;
//  }
//}