//package spring.backend.library.util;
//
//import java.io.IOException;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//
//public final class ResponseUtil {
//
//  public static void writeResponse(HttpStatus httpStatus, HttpServletResponse response, ResponseMsg responseMsg)
//      throws IOException {
//    response.setContentType("application/json;charset=UTF-8");
//    response.setStatus(httpStatus.value());
//    response.getWriter().write(ObjectMapperUtil.writeValueAsString(responseMsg));
//  }
//}