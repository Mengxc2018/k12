package cn.k12soft.servo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by xfnjlove on 2017/12/26.
 */
@Entity
@DynamicUpdate
public class Marquee extends SchoolEntity {

  private Marquee() {

  }

  public Marquee(School school,
                 String firstImg,
                 String centerImg,
                 String lastImg) {

    super(school.getId());
    this.firstImg = firstImg;
    this.centerImg = centerImg;
    this.lastImg = lastImg;
  }

  @Id
  @GeneratedValue
  private Integer id;

  private String firstImg;

  private String centerImg;

  private String lastImg;

  public String getFirstImg() {
    return firstImg;
  }

  public void setFirstImg(String firstImg) {
    this.firstImg = firstImg;
  }

  public String getCenterImg() {
    return centerImg;
  }

  public void setCenterImg(String centerImg) {
    this.centerImg = centerImg;
  }

  public String getLastImg() {
    return lastImg;
  }

  public void setLastImg(String lastImg) {
    this.lastImg = lastImg;
  }

  public Integer getId() {
    return id;
  }

}
