package spring.backend.library.config.security;

import com.google.common.collect.ImmutableList;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import spring.backend.library.config.filter.AccessDeniedHandle;
import spring.backend.library.config.filter.AuthenticationEntryPointHandle;
import spring.backend.library.config.filter.JwtFilter;
import spring.backend.library.config.filter.JwtProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {

  private final AccessDeniedHandle accessDeniedHandle;

  private final AuthenticationEntryPointHandle authenticationEntryPointHandle;

  private final SecretKey secretKey;

  public Config(AccessDeniedHandle accessDeniedHandle,
      AuthenticationEntryPointHandle authenticationEntryPointHandle, SecretKey secretKey
  ) {
    this.accessDeniedHandle = accessDeniedHandle;
    this.authenticationEntryPointHandle = authenticationEntryPointHandle;
    this.secretKey = secretKey;
  }

  protected void configure(HttpSecurity http) throws Exception {
    http
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    ;

    http.csrf().disable()
        .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().accessDeniedHandler(accessDeniedHandle)
        .and()
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandle)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST,"/users").permitAll()
        .antMatchers(HttpMethod.POST,"/users/login,").permitAll()
        .and().authorizeRequests().anyRequest().authenticated();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/users/login", "/users");
  }

}

