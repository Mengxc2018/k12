package cn.k12soft.servo.configuration;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("cn.k12soft.servo")
@EnableTransactionManagement
public class DatabaseConfiguration {

  private final Environment env;

  public DatabaseConfiguration(Environment env) {
    this.env = env;
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server h2TCPServer() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers");
  }
}
