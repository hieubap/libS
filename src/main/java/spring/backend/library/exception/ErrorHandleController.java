package spring.backend.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.backend.library.dto.ResponseEntity;

@RestControllerAdvice
public class ErrorHandleController extends ResponseEntityExceptionHandler{

//  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(BaseException.class)
//  public ResponseEntity handleBaseException(BaseException ex) {
//    ResponseEntity responseBody = ex.getResponseBody();
//
//    return responseBody;
//  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity handleException(BaseException ex) {
    ResponseEntity responseBody = new ResponseEntity();
    responseBody.setCode(ex.getResponseBody().getCode());
    responseBody.setMessage(ex.getResponseBody().getMessage());

    return responseBody;
  }
}
