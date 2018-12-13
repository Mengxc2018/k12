package cn.k12soft.servo.domain.iclock;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Entity
public class ATTLog {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sn")
  private IclockDevice device;

  private int pin;

  private Instant time;

  private int status;

  private int verifyCode;

  private ATTLog() {
  }

  public ATTLog(IclockDevice device, int pin, Instant time, int status, int verifyCode) {
    this.device = device;
    this.pin = pin;
    this.time = time;
    this.status = status;
    this.verifyCode = verifyCode;
  }

  public Long getId() {
    return id;
  }

  public IclockDevice getDevice() {
    return device;
  }

  public int getPin() {
    return pin;
  }

  public Instant getTime() {
    return time;
  }

  public int getStatus() {
    return status;
  }

  public int getVerifyCode() {
    return verifyCode;
  }
}
