package spring.backend.library.config.security;

import com.google.common.collect.ImmutableList;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandle)
        .and()
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandle)
        .and()
        .authorizeRequests()
        .antMatchers("/users/login", "/users").permitAll()
        .and().authorizeRequests().anyRequest().authenticated();

    http.addFilterBefore(new JwtFilter(secretKey), BasicAuthenticationFilter.class);

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/users/login", "/users");
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(ImmutableList.of("*"));
    configuration
        .setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
    // setAllowCredentials(true) is important, otherwise:
    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
    configuration.setAllowCredentials(true);
    // setAllowedHeaders is important! Without it, OPTIONS preflight request
    // will fail with 403 Invalid CORS request
    configuration
        .setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}

