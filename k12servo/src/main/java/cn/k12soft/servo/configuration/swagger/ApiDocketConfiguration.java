package cn.k12soft.servo.configuration.swagger;

import cn.k12soft.servo.security.Active;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class ApiDocketConfiguration extends WebMvcConfigurerAdapter {

  private static final Logger log = LoggerFactory.getLogger(ApiDocketConfiguration.class);

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // support swagger ui resources
    registry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }


  @Bean
  public Docket swaggerSpringfoxDocket() {
    log.debug("Starting Swagger");
    StopWatch watch = new StopWatch();
    watch.start();
    Docket docket = new Docket(DocumentationType.SWAGGER_2)
      .useDefaultResponseMessages(false)
      .directModelSubstitute(OffsetDateTime.class, String.class)
      .directModelSubstitute(Duration.class, String.class)
      .directModelSubstitute(LocalDate.class, String.class)
      .directModelSubstitute(Timestamp.class, Long.class)
      .directModelSubstitute(Instant.class, Long.class)
      .forCodeGeneration(true)
      .ignoredParameterTypes(Active.class)
      .select()
      .build();
    watch.stop();
    log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
    return docket;
  }
}
