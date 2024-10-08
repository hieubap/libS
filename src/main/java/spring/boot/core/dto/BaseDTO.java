package spring.boot.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO {
  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long id;

  @ApiModelProperty(hidden = true)
  @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
  @JsonSerialize(using = ZonedDateTimeSerializer.class)
  private ZonedDateTime createdAt;

  @ApiModelProperty(hidden = true)
  @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
  @JsonSerialize(using = ZonedDateTimeSerializer.class)
  private ZonedDateTime updatedAt;

  @ApiModelProperty(hidden = true)
  private Long createdBy;

  @ApiModelProperty(hidden = true)
  private Long updatedBy;

  protected Short active;

  public void setNull(){
    this.createdAt = null;
    this.createdBy = null;
    this.updatedAt = null;
    this.updatedBy = null;

  }
}
