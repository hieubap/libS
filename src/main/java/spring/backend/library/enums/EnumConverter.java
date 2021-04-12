package spring.backend.library.enums;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import javax.persistence.AttributeConverter;
import org.springframework.data.util.CastUtils;
import spring.backend.library.enums.IEnum;

public class EnumConverter<E extends IEnum> implements AttributeConverter<E, Short> {

  private final Class<E> enumClass;

  public EnumConverter() {
    enumClass = CastUtils
        .cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  @Override
  public Short convertToDatabaseColumn(E enumObj) {
    if (enumObj == null) {
      return null;
    }
    return enumObj.getValue();
  }

  @Override
  public E convertToEntityAttribute(Short dbData) {
    if (dbData != null) {
      return Arrays.stream(enumClass.getEnumConstants()).filter(e -> {
        if (e.getValue() == dbData) {
          return true;
        }
        throw new IllegalArgumentException("Unknown database value:" + dbData);

      })
          .findFirst().get();
    }
    return null;
  }
}
