package spring.backend.library.config.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.backend.library.config.security.PropertiesConfiguration;
import spring.backend.library.config.userdetail.Authority;
import spring.backend.library.config.userdetail.UserDetail;

@Service
public class JwtProvider {

  public JwtProvider(SecretKey secretKey) {
    this.secretKey = secretKey;
  }

  private final SecretKey secretKey;

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
    for (String key : jwtAdditionalInformation.keySet()) {
      jwtBuilder.claim(key, jwtAdditionalInformation.get(key));
      additionalInformation.put(key, jwtAdditionalInformation.get(key));

    }

    String jwt = jwtBuilder
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
        .signWith(secretKey)
        .compact();

    UserDetail userDetail = new UserDetail();
    userDetail.setId(id);
    userDetail.setUsername(username);
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String s : privileges
    ) {
      authorities.add(new Authority(s));
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userDetail,
        null,
        authorities
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    additionalInformation.put("tokenId", UUID.randomUUID().toString());
    additionalInformation.put("token", jwt);
    additionalInformation.put("username", username);
    additionalInformation.put("full_name", fullName);
    additionalInformation.put("authorities", privileges);

    return additionalInformation;
  }

  public UsernamePasswordAuthenticationToken getAuthentication( String token) {

    JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);

     Jws claimsJws = jwtParser.parseClaimsJws(token);

     Claims claims = (Claims) claimsJws.getBody();

    Long id = Long.valueOf(claims.get("id").toString());
    String username = (String) claims.get("username");
     Collection authorities =
        Arrays.stream(claims.get("authorities").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UserDetail userDetails = new UserDetail();

    userDetails.setId(id);
    userDetails.setUsername(username);
    userDetails.setAuthorities(authorities);
    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
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