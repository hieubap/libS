package spring.backend.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long id;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createAt;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updateAt;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long createdBy;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long updatedBy;
}
