package cn.k12soft.servo.module.employees.domain;

import cn.k12soft.servo.module.duty.domain.Duty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Duty duty;     // 职位Id
    private Integer actorId;    // 当前员工
    private Integer actorNum;   // 员工工号
    private Integer parentActorId;    // 该员工的上级id
    private Integer schoolId;   // 学校Id

    @OneToOne(cascade = CascadeType.ALL)
    private EmployeeBasic employeeBasic;
    private boolean isShow;     // 是否显示

    private String overtime;    // 加班时间
    private String rest;        // 调休时间
    private String annual;      // 年假时间
    private String sick;        // 病假时间
    private String barth;       // 产假
    private String barthWith;   // 陪产假
    private String marry;       // 婚假
    private String funeral;     // 丧假

    private Instant createdAt;  // 创建时间

    public Employee() {
    }

    public Employee(Duty duty, Integer actorId, Integer actorNum, Integer parentActorId, Integer schoolId, boolean isShow, EmployeeBasic employeeBasic, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral, Instant createdAt) {
        this.duty = duty;
        this.actorId = actorId;
        this.actorNum = actorNum;
        this.parentActorId = parentActorId;
        this.schoolId = schoolId;
        this.isShow = isShow;
        this.employeeBasic = employeeBasic;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
        this.createdAt = createdAt;
    }

    public Employee(Long id, Duty duty, Integer actorId, Integer actorNum, Integer parentActorId, Integer schoolId, boolean isShow, EmployeeBasic employeeBasic, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral, Instant createdAt) {
        this.id = id;
        this.duty = duty;
        this.actorId = actorId;
        this.actorNum = actorNum;
        this.parentActorId = parentActorId;
        this.schoolId = schoolId;
        this.isShow = isShow;
        this.employeeBasic = employeeBasic;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Integer getActorNum() {
        return actorNum;
    }

    public void setActorNum(Integer actorNum) {
        this.actorNum = actorNum;
    }

    public Integer getParentActorId() {
        return parentActorId;
    }

    public void setParentActorId(Integer parentActorId) {
        this.parentActorId = parentActorId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public EmployeeBasic getEmployeeBasic() {
        return employeeBasic;
    }

    public void setEmployeeBasic(EmployeeBasic employeeBasic) {
        this.employeeBasic = employeeBasic;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
