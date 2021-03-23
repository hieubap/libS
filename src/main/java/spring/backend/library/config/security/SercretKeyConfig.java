package spring.backend.library.config.security;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class SercretKeyConfig {

  @Autowired
  private PropertiesConfiguration propertiesConfiguration;

  @Bean
  public SecretKey secretKey(){
    return Keys.hmacShaKeyFor(propertiesConfiguration.getSecretKey().getBytes());
  }
}
