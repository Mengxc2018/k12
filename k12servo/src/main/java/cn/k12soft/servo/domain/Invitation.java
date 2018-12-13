package cn.k12soft.servo.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author <a href="mailto:wangfenghua@bonusstudio.com">fenghua.wang</a> Created on 2017/7/15.
 */
@Entity
@DynamicUpdate
public class Invitation implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Integer id;

  @OneToOne
  private Actor actor;

  @Column(nullable = false, unique = true, length = 11)
  private String mobile;

  @Column(nullable = false)
  private Integer secretCode;

  public Integer getId() {
    return id;
  }

  public Actor getActor() {
    return actor;
  }

  public Invitation actor(Actor user) {
    setActor(user);
    return this;
  }

  public String getMobile() {
    return mobile;
  }

  public Invitation mobile(String mobile) {
    setMobile(mobile);
    return this;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getSecretCode() {
    return secretCode;
  }

  public Invitation secretCode(int secretCode) {
    setSecretCode(secretCode);
    return this;
  }

  public void setSecretCode(Integer secretCode) {
    this.secretCode = secretCode;
  }

  public void setActor(Actor actor) {
    this.actor = actor;
  }
}
