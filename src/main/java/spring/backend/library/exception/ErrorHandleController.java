package spring.backend.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.backend.library.config.filter.AuthenticationEntryPointHandle;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.msg.Message;

@RestControllerAdvice
public class ErrorHandleController extends ResponseEntityExceptionHandler{

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleBaseException(Exception ex) {
    ResponseEntity responseBody = new ResponseEntity(500,ex.getMessage());

    return responseBody;
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity handleAccessDeniedException(AccessDeniedException ex) {
    ResponseEntity responseBody = new ResponseEntity(401,
        Message.getMessage("AccessDeniedHandle.handleAccessDeniedException"));

    return responseBody;
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity handleInvalidToken(InvalidTokenException ex) {
    ResponseEntity responseBody = new ResponseEntity(402,
        Message.getMessage("AuthenticationEntryPointImpl.invalidToken"));

    return responseBody;
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity handleException(BaseException ex) {
    ResponseEntity responseBody = new ResponseEntity();
    responseBody.setCode(ex.getResponseBody().getCode());
    responseBody.setMessage(ex.getResponseBody().getMessage());

    return responseBody;
  }
}
