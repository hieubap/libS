package spring.backend.library.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {

  public static void writeResponse(HttpStatus httpStatus, HttpServletResponse response,
      String responseMsg)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(responseMsg.toString());
  }
}
