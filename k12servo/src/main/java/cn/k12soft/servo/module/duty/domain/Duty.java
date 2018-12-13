package cn.k12soft.servo.module.duty.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.TRUE_FALSE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Duty {
    @Id
    @GeneratedValue
    private Long id;
    private String name;    // 职务名称
    private Integer schoolId;    // 学校ID
    private TRUE_FALSE isSubstratum;    // 是否基层职务:true是、false不是

    public Duty(){}

    public Duty(String name, Integer schoolId, TRUE_FALSE isSubstratum) {
        this.name = name;
        this.schoolId = schoolId;
        this.isSubstratum = isSubstratum;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public TRUE_FALSE getIsSubstratum() {
        return isSubstratum;
    }

    public void setIsSubstratum(TRUE_FALSE isSubstratum) {
        this.isSubstratum = isSubstratum;
    }
}
