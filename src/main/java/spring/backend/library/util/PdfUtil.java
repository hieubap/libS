//package spring.backend.library.util;
//
//import com.aspose.pdf.Document;
//import com.aspose.pdf.HtmlLoadOptions;
//import com.aspose.pdf.PageSize;
//import java.io.ByteArrayInputStream;
//import java.nio.file.Path;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public final class PdfUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(PdfUtil.class);
//
//  public static void htmlToPdf(String html, Path path) {
//    if (StringUtil.isBlank(html)) {
//      return;
//    }
//
//    HtmlLoadOptions options = new HtmlLoadOptions();
//    options.getPageInfo().setWidth(PageSize.getA4().getWidth());
//    options.getPageInfo().setHeight(PageSize.getA4().getHeight());
//    options.getPageInfo().getMargin().setTop(20);
//    options.getPageInfo().getMargin().setBottom(20);
//    options.getPageInfo().getMargin().setLeft(20);
//    options.getPageInfo().getMargin().setRight(20);
//
//    Document doc = new Document(new ByteArrayInputStream(html.getBytes()), options);
//
//    doc.save(path.toString());
//  }
//}