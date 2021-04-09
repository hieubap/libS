package spring.backend.library.config.security;

import javax.crypto.SecretKey;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.backend.library.config.filter.AccessDeniedHandle;
import spring.backend.library.config.filter.AuthenticationEntryPointHandle;
import spring.backend.library.config.filter.JwtFilter;

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
        .cors()
        .and()
        .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().accessDeniedHandler(accessDeniedHandle)
        .and()
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandle)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST,"/users").permitAll()
        .antMatchers("/users/login,").permitAll()
        .and().authorizeRequests().anyRequest().authenticated();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/users/login", "/users");
  }

}

