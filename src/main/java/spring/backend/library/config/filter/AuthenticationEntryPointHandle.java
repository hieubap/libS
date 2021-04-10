package spring.backend.library.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.msg.Message;
import spring.backend.library.utils.ResponseUtil;

@Component
public final class AuthenticationEntryPointHandle implements
    AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException {
    ResponseEntity response = new ResponseEntity(401,
        Message.getMessage("AuthenticationEntryPointImpl.invalidToken"));

    ObjectMapper objectMapper = new ObjectMapper();
    ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, httpServletResponse,
        objectMapper.writeValueAsString(response));
  }
}
