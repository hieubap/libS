package spring.backend.library.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {

  public static void writeResponse(HttpStatus httpStatus, HttpServletResponse response,
      Object responseMsg)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(responseMsg.toString());
  }

  public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
