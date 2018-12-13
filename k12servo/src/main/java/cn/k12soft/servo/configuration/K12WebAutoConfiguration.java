package cn.k12soft.servo.configuration;

import static org.springframework.web.cors.CorsConfiguration.ALL;

import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(K12Properties.class)
public class K12WebAutoConfiguration extends WebMvcConfigurerAdapter {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
    registrar.setUseIsoFormat(true);
    registrar.registerFormatters(registry);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(ALL)
      .allowedMethods(ALL)
      .allowedHeaders(ALL)
      .maxAge(3600);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(authenticationPrincipalArgumentResolver());
  }

  @Bean
  AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
    return new AuthenticationPrincipalArgumentResolver();
  }
}
