package spring.backend.library.config.filter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

  @Autowired
  private SecretKey secretKey;

  public Map<String, Object> generateToken(JwtTokenProperties jwtTokenProperties) {
    Long id = jwtTokenProperties.getId();
    String username = jwtTokenProperties.getUsername();
    String fullName = jwtTokenProperties.getFullName();
    List<String> privileges = jwtTokenProperties.getPrivileges();
    Map<String, Object> jwtAdditionalInformation = jwtTokenProperties.getJwtAdditionalInformation();
    Map<String, Object> additionalInformation = jwtTokenProperties.getAdditionalInformation();

    if (jwtAdditionalInformation == null) {
      jwtAdditionalInformation = new HashMap<>();
    }

    if (additionalInformation == null) {
      additionalInformation = new HashMap<>();
    }

    if (privileges == null) {
      privileges = new ArrayList<>();
    }

    if (id != null && id > 0) {
      privileges.add("ROLE_Authenticated");
    }

    if (!privileges.contains("ROLE_Unauthenticated")) {
      privileges.add("ROLE_Unauthenticated");
    }
    JwtBuilder jwtBuilder = Jwts.builder()
        .claim("id", id)
        .claim("username", username)
        .claim("fullname", fullName)
        .claim("authorities", privileges);
    if (jwtAdditionalInformation != null) {
      for (String key : jwtAdditionalInformation.keySet()) {
        jwtBuilder.claim(key, jwtAdditionalInformation.get(key));
        additionalInformation.put(key, jwtAdditionalInformation.get(key));
      }
    }

    String jwt = jwtBuilder
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
        .signWith(secretKey)
        .compact();

    additionalInformation.put("tokenId", UUID.randomUUID().toString());
    additionalInformation.put("token", jwt);
    additionalInformation.put("username", username);
    additionalInformation.put("full_name", fullName);
    additionalInformation.put("authorities", privileges);

    return additionalInformation;
  }

  @Getter
  @Builder
  public static class JwtTokenProperties {

    private final Long id;

    private final String username;

    private final String fullName;

    private final List<String> privileges;

    private final Map<String, Object> additionalInformation;

    private final Map<String, Object> jwtAdditionalInformation;

  }
}