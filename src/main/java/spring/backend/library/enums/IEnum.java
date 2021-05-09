package spring.backend.library.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface IEnum {

//  @JsonValue
  short getValue();

  String getName();
}
