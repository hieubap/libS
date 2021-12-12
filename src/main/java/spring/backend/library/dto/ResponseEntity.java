package spring.backend.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseEntity {
  private Integer code = 0;
  private String message = null;
  private Object data = null;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Long totalElements;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Integer totalPages;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Integer pageNumber;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Integer pageSize;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Integer numberOfElements;

  public ResponseEntity(Integer code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
  public ResponseEntity(Integer code, String message){
    this(code, message, null);
  }
}
