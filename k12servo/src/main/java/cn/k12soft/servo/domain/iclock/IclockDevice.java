package cn.k12soft.servo.domain.iclock;

import cn.k12soft.servo.domain.School;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Entity
public class IclockDevice {

  @Id
  private String sn;

  @ManyToOne
  private School school;

  private IclockDevice() {
  }

  public IclockDevice(String sn, School school) {
    this.sn = sn;
    this.school = school;
  }

  public String getSn() {
    return sn;
  }

  public School getSchool() {
    return school;
  }
}
