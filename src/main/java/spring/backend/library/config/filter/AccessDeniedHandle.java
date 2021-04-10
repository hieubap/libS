package spring.backend.library.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.msg.Message;
import spring.backend.library.utils.ResponseUtil;

@Component
public class AccessDeniedHandle implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException {
    ResponseEntity response = new ResponseEntity(403, Message
        .getMessage("AccessDeniedHandle.handleAccessDeniedException"));

    ResponseUtil.writeResponse(HttpStatus.OK,httpServletResponse,
        ResponseUtil.convertObjectToJson(response));

  }
}
