package cn.k12soft.servo.service.dto;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
public class SchoolDTO {

  private Integer id;
  private String name;
  private String desc;

  private Integer formDate; // 考核周期开始日期
  private Integer toDate;   // 考核周期结束时间

  private String annual;      // 年假时间
  private String sick;        // 病假时间
  private String barth;       // 产假
  private String barthWith;   // 陪产假
  private String marry;       // 婚假
  private String funeral;     // 丧假

  public Integer getId() {
    return id;
  }

  public SchoolDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public SchoolDTO setName(String name) {
    this.name = name;
    return this;
  }

  public String getDesc() {
    return desc;
  }

  public SchoolDTO setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  public Integer getFormDate() {
    return formDate;
  }

  public SchoolDTO setFormDate(Integer formDate) {
    this.formDate = formDate;
    return this;
  }

  public Integer getToDate() {
    return toDate;
  }

  public SchoolDTO setToDate(Integer toDate) {
    this.toDate = toDate;
    return this;
  }

  public String getAnnual() {
    return annual;
  }

  public SchoolDTO setAnnual(String annual) {
    this.annual = annual;
    return this;
  }

  public String getSick() {
    return sick;
  }

  public SchoolDTO setSick(String sick) {
    this.sick = sick;
    return this;
  }

  public String getBarth() {
    return barth;
  }

  public SchoolDTO setBarth(String barth) {
    this.barth = barth;
    return this;
  }

  public String getBarthWith() {
    return barthWith;
  }

  public SchoolDTO setBarthWith(String barthWith) {
    this.barthWith = barthWith;
    return this;
  }

  public String getMarry() {
    return marry;
  }

  public SchoolDTO setMarry(String marry) {
    this.marry = marry;
    return this;
  }

  public String getFuneral() {
    return funeral;
  }

  public SchoolDTO setFuneral(String funeral) {
    this.funeral = funeral;
    return this;
  }
}
