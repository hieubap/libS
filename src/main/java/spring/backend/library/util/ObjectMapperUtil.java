//package spring.backend.library.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.util.CastUtils;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import vn.isofh.common.config.databind.DoubleModule;
//import vn.isofh.common.config.databind.KeepDataTypeModule;
//import vn.isofh.common.config.databind.StringModule;
//import vn.isofh.common.enums.EnumModule;
//import vn.isofh.common.exception.ObjectMapperException;
//
//public class ObjectMapperUtil {
//
//  private final static Logger logger = LoggerFactory.getLogger(ObjectMapperUtil.class);
//
//  public static ObjectMapper mapper = getMapper();
//
//  public static ObjectMapper keepDataTypeMapper = getKeepDataTypeMapper();
//
//  public static ObjectMapper getKeepDataTypeMapper() {
//    return new ObjectMapper() {{
//      registerModule(new KeepDataTypeModule());
//    }}; // jackson's objectmapper
//  }
//
//  public static ObjectMapper getMapper() {
//    return new ObjectMapper() {{
//      disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//      disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//      enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
//      enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//      setTimeZone(DateUtil.getDefaultTimeZone());
//
//      registerModule(new JavaTimeModule());
//      registerModule(new StringModule());
//      registerModule(new EnumModule());
//      registerModule(new DoubleModule());
//    }}; // jackson's objectmapper
//  }
//
//  public static ObjectNode createObjectNode() {
//    return mapper.createObjectNode();
//  }
//
//  public static ArrayNode createArrayNode() {
//    return mapper.createArrayNode();
//  }
//
//  public static List<HttpMessageConverter<?>> getMessageConverters(ObjectMapper mapper) {
//    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//    MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
//    jsonMessageConverter.setObjectMapper(mapper == null ? ObjectMapperUtil.mapper : mapper);
//    messageConverters.add(jsonMessageConverter);
//
//    return messageConverters;
//  }
//
//    public static <T> T convertValue(Map<String, Object> fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    return mapper.convertValue(fromValue, toValueType);
//  }
//
//  public static <T> T convertValue(String fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    try {
//      return mapper.readValue(fromValue, toValueType);
//    } catch (IOException e) {
//      throw new ObjectMapperException.ConvertToObject(fromValue, toValueType.getName(), e);
//    }
//  }
//
//  public static ObjectNode convertValue(String fromValue) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    try {
//      return (ObjectNode) mapper.readTree(fromValue);
//    } catch (IOException e) {
//      throw new ObjectMapperException.ConvertToObject(fromValue, JsonNode.class.getName(), e);
//    }
//  }
//
//  public static Map<String, Object> convertValue(Object fromValue) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    return CastUtils.cast(mapper.convertValue(fromValue, Map.class));
//  }
//
//  public static Map<String, Object> keepDataTypeConvertValue(Object fromValue) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    return CastUtils.cast(keepDataTypeMapper.convertValue(fromValue, Map.class));
//  }
//
//  public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    return mapper.convertValue(fromValue, toValueType);
//  }
//
//  public static <T> List<T> convertArrayListValue(Object fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    Class<T[]> clazz = CastUtils.cast(Array.newInstance(toValueType, 0).getClass());
//
//    return new ArrayList<>(Arrays.asList(convertValue(fromValue, clazz)));
//  }
//
//  public static <T> Set<T> convertHashSetValue(Object fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    Class<T[]> clazz = CastUtils.cast(Array.newInstance(toValueType, 0).getClass());
//
//    return new HashSet<>(Arrays.asList(convertValue(fromValue, clazz)));
//  }
//
//  public static <T> List<T> convertArrayListValue(String fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    Class<T[]> clazz = CastUtils.cast(Array.newInstance(toValueType, 0).getClass());
//
//    T[] list = convertValue(fromValue, clazz);
//
//    if (list == null) {
//      return null;
//    }
//
//    return new ArrayList<>(Arrays.asList(list));
//  }
//
//  public static <T> Set<T> convertHashSetValue(String fromValue, Class<T> toValueType) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    Class<T[]> clazz = CastUtils.cast(Array.newInstance(toValueType, 0).getClass());
//
//    return new HashSet<>(Arrays.asList(convertValue(fromValue, clazz)));
//  }
//
//  public static String writeValueAsString(Object fromValue) {
//    if (fromValue == null) {
//      return null;
//    }
//
//    try {
//      return mapper.writeValueAsString(fromValue);
//    } catch (JsonProcessingException e) {
//      throw new ObjectMapperException.WriteValue(fromValue.getClass().getName(), e);
//    }
//  }
//
//  public static <T> List<T> convertValues(List<Map<String, Object>> fromValue,
//      Class<T> toValueType) {
//    if (fromValue == null) {
//      return new ArrayList<>();
//    }
//
//    List<T> toList = new ArrayList<>();
//    for (Map<String, Object> map : fromValue) {
//      toList.add(convertValue(map, toValueType));
//    }
//
//    return toList;
//  }
//
//  public static List<Map<String, Object>> convertValues(List<Object> fromValue) {
//    if (fromValue == null) {
//      return new ArrayList<>();
//    }
//
//    List<Map<String, Object>> toList = new ArrayList<>();
//    for (Object obj : fromValue) {
//      toList.add(convertValue(obj));
//    }
//
//    return toList;
//  }
//
//
//}