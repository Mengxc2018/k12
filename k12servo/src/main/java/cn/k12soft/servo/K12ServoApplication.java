package cn.k12soft.servo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackageClasses = {K12ServoApplication.class, Jsr310JpaConverters.class})
@EnableScheduling
public class K12ServoApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(K12ServoApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.setRegisterShutdownHook(true);
    app.run(args);
  }
}