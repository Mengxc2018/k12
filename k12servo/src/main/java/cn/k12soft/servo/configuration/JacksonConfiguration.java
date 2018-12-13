package cn.k12soft.servo.configuration;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

  @Bean
  public Hibernate5Module hibernate5Module() {
    return new Hibernate5Module();
  }

  @Bean
  public AfterburnerModule afterburnerModule() {
    return new AfterburnerModule();
  }
}
