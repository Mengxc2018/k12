package cn.k12soft.servo.module.employees.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * 员工入职基本信息
 */
@Entity
@DynamicUpdate
public class EmployeeBasic{
    @Id
    @GeneratedValue
    private Long id;
    private Instant joinAt;     // 入职时间
    private String probation;   // 试用期
    private Instant officialAt; // 转正时间
    private boolean isOfficial; // 是否转正
    private boolean isGraduate; // 是否毕业
    private boolean hasDoploma; // 是否有毕业证
    private boolean isLeave;    // 是否离职
    private Instant leaveAt;    // 离职时间
    private boolean hasSocial;  // 有无社保
    private Integer schoolId;

    public EmployeeBasic(Instant joinAt, Instant leaveAt, Instant officialAt, String probation, boolean isOfficial, boolean isGraduate, boolean hasDoploma, Integer schoolId) {
        this.joinAt = joinAt;
        this.leaveAt = leaveAt;
        this.officialAt = officialAt;
        this.probation = probation;
        this.isOfficial = isOfficial;
        this.hasDoploma = hasDoploma;
        this.isGraduate = isGraduate;
        this.schoolId = schoolId;
    }

    public EmployeeBasic(Instant joinAt, boolean official, boolean graduate, boolean hasDiploma, Integer schoolId) {
        this.joinAt = joinAt;
        this.isOfficial = official;
        this.hasDoploma = hasDiploma;
        this.isGraduate = graduate;
        this.schoolId = schoolId;
    }

    public EmployeeBasic() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Instant joinAt) {
        this.joinAt = joinAt;
    }

    public String getProbation() {
        return probation;
    }

    public void setProbation(String probation) {
        this.probation = probation;
    }

    public Instant getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(Instant leaveAt) {
        this.leaveAt = leaveAt;
    }

    public Instant getOfficialAt() {
        return officialAt;
    }

    public void setOfficialAt(Instant officialAt) {
        this.officialAt = officialAt;
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    public boolean isGraduate() {
        return isGraduate;
    }

    public void setGraduate(boolean graduate) {
        isGraduate = graduate;
    }

    public boolean isHasDoploma() {
        return hasDoploma;
    }

    public void setHasDoploma(boolean hasDoploma) {
        this.hasDoploma = hasDoploma;
    }

    public boolean isLeave() {
        return isLeave;
    }

    public void setLeave(boolean leave) {
        isLeave = leave;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public boolean isHasSocial() {
        return hasSocial;
    }

    public void setHasSocial(boolean hasSocial) {
        this.hasSocial = hasSocial;
    }
}
