package spring.backend.library.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface IEnum {

  short getValue();

  @JsonValue
  String getName();
}
