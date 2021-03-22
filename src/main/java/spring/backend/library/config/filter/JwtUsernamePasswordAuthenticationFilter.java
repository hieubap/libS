package spring.backend.library.config.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.backend.library.config.security.PropertiesConfiguration;
import spring.backend.library.config.userdetail.UserDetail;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final PropertiesConfiguration propertiesConfiguration;
  private final SecretKey secretKey;
  private final JwtProvider jwtService;

  public JwtUsernamePasswordAuthenticationFilter(
      AuthenticationManager authenticationManager,
      PropertiesConfiguration propertiesConfiguration, SecretKey secretKey,
      JwtProvider jwtService) {
    this.authenticationManager = authenticationManager;
    this.propertiesConfiguration = propertiesConfiguration;
    this.secretKey = secretKey;
    this.jwtService = jwtService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader(propertiesConfiguration.getAuthorizationHeader());

    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token);

      Claims body = claimsJws.getBody();
      UserDetail userDetail = new UserDetail();
      userDetail.setId(((Number) body.get("id")).longValue());
      userDetail.setUsername((String) body.get("username"));

      List<Map<String, String>> authority = (List<Map<String, String>>) body.get("authorities");

      Set<SimpleGrantedAuthority> grantetAuth = authority.stream()
          .map(m -> new SimpleGrantedAuthority(m.get("authority")))
          .collect(Collectors.toSet());

      Authentication authentication = new UsernamePasswordAuthenticationToken(
          userDetail,
          null,
          grantetAuth
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JwtException e) {
      return;
    }
    chain.doFilter(request, response);
  }
}
