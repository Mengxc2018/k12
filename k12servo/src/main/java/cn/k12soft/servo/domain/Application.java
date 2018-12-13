package cn.k12soft.servo.domain;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Application {

  @Id
  private String name;

  @Column(nullable = false)
  private Instant createdAt;

  Application() {
  }

  public Application(String name) {
    this.name = name;
    this.createdAt = Instant.now();
  }

  public String getName() {
    return name;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
