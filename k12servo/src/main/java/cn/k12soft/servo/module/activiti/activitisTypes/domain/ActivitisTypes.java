package cn.k12soft.servo.module.activiti.activitisTypes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class ActivitisTypes {

    @Id
    @GeneratedValue
    private Long id;
    private String name;    // 类别名
    private Long typeNo;    // 标识
    private Integer schoolId;
    private Instant createAt;

    public ActivitisTypes(){}

    public ActivitisTypes(String name, Long typeNo, Integer schoolId,Instant createAt) {
        this.name = name;
        this.typeNo = typeNo;
        this.schoolId = schoolId;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getTypeNo() {
        return typeNo;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Instant getCreateAt() {
        return createAt;
    }
}
