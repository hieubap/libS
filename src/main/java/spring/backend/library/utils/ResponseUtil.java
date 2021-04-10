package spring.backend.library.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import spring.backend.library.dto.ResponseEntity;

public final class ResponseUtil {

  public static void writeResponse(HttpStatus httpStatus, HttpServletResponse response,
      String responseMsg)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(responseMsg.toString());
  }
  public static void writeResponse(HttpStatus httpStatus, HttpServletResponse response,
      ResponseEntity responseEntity)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(responseEntity.toString());
  }
}
