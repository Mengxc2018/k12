package cn.k12soft.servo.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

import cn.k12soft.servo.module.department.domain.Dept;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class School {

  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  @Column(name = "`desc`")
  private String desc;
  private Integer cityId; // 所属城市
  private String cityName;  // 城市名
  @Column
  private Integer parentId;
  private String code;      // 学校编码
  private Integer formDate; // 考核周期开始日期
  private Integer toDate;   // 考核周期结束时间
  private String annual;      // 年假时间
  private String sick;        // 病假时间
  private String barth;       // 产假
  private String barthWith;   // 陪产假
  private String marry;       // 婚假
  private String funeral;     // 丧假
  private long lastCalcTeacherPayoutTime; // 每日计算老师工资写入支出表的时间
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Dept> department = new HashSet<>();


  public School() {
  }

  public School(Integer id) {
    this.id = id;
  }

  public School(String name,
                String desc,
                Integer formDate,
                Integer toDate) {
    this.name = name;
    this.desc = desc;
    this.formDate = formDate;
    this.toDate = toDate;
  }

  public School(String name, String desc, Integer formDate, Integer toDate, String annual, String sick, String barth, String barthWith, String marry, String funeral) {
    this.name = name;
    this.desc = desc;
    this.formDate = formDate;
    this.toDate = toDate;
    this.annual = annual;
    this.sick = sick;
    this.barth = barth;
    this.barthWith = barthWith;
    this.marry = marry;
    this.funeral = funeral;
  }

  public School(String name,
                String desc) {
    this.name = name;
    this.desc = desc;
  }

  public School(String name,
                String desc,
                int cityId,
                String cityName,
                String code) {
    this.name = name;
    this.desc = desc;
    this.cityId = cityId;
    this.cityName = cityName;
    this.code = code;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public School name(String name) {
    setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public School desc(String desc) {
    setDesc(desc);
    return this;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public School setId(Integer id) {
    this.id = id;
    return this;
  }

  public Integer getFormDate() {
    return formDate;
  }

  public School setFormDate(Integer formDate) {
    this.formDate = formDate;
    return this;
  }

  public Integer getToDate() {
    return toDate;
  }

  public void setToDate(Integer toDate) {
    this.toDate = toDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof School)) {
      return false;
    }
    School school = (School) o;
    return Objects.equals(getId(), school.getId());
  }

  public String getAnnual() {
    return annual;
  }

  public void setAnnual(String annual) {
    this.annual = annual;
  }

  public String getSick() {
    return sick;
  }

  public void setSick(String sick) {
    this.sick = sick;
  }

  public String getBarth() {
    return barth;
  }

  public void setBarth(String barth) {
    this.barth = barth;
  }

  public String getBarthWith() {
    return barthWith;
  }

  public void setBarthWith(String barthWith) {
    this.barthWith = barthWith;
  }

  public String getMarry() {
    return marry;
  }

  public void setMarry(String marry) {
    this.marry = marry;
  }

  public String getFuneral() {
    return funeral;
  }

  public void setFuneral(String funeral) {
    this.funeral = funeral;
  }

  public long getLastCalcTeacherPayoutTime() {
    return lastCalcTeacherPayoutTime;
  }

  public void setLastCalcTeacherPayoutTime(long lastCalcTeacherPayoutTime) {
    this.lastCalcTeacherPayoutTime = lastCalcTeacherPayoutTime;
  }

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  public Set<Dept> getDepartment() {
    return department;
  }


  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public void setDepartment(Set<Dept> department) {
    this.department = department;
  }

  @Override
  public String toString() {
    return "School{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", desc='" + desc + '\'' +
            ", cityId=" + cityId +
            ", cityName='" + cityName + '\'' +
            ", code='" + code + '\'' +
            ", formDate=" + formDate +
            ", toDate=" + toDate +
            ", annual='" + annual + '\'' +
            ", sick='" + sick + '\'' +
            ", barth='" + barth + '\'' +
            ", barthWith='" + barthWith + '\'' +
            ", marry='" + marry + '\'' +
            ", funeral='" + funeral + '\'' +
            ", lastCalcTeacherPayoutTime=" + lastCalcTeacherPayoutTime +
            ", departments=" + department +
            '}';
  }
}
