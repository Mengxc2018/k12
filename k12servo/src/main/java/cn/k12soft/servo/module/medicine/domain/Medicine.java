package cn.k12soft.servo.module.medicine.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/**
 * 喂药
 */
@Entity
@DynamicUpdate
public class Medicine {
    @Id
    @GeneratedValue
    private Long id;
    private Integer actorId;        // 创建人
    private Integer carryActorId;   // 执行人actorid
    private Integer stuId;
    private String stuName;         // 服药儿童姓名
    private String medicineName;    // 药品名称
    private String dose;        // 服药剂量
    private Instant executeTime;    // 执行时间
    @OneToMany(fetch = FetchType.EAGER, cascade=ALL)
    private Set<MedicineTime> medicineTime; // 服药时间
    private boolean isOver;         // 是否全部服用
    private boolean isStop;         // 是否停药
    private Integer days;           // 吃药天数
    private Integer klassId;
    private Integer schoolId;
    private String remark;
    private Instant createdAt;

    public Medicine() {
    }

    public Medicine(Integer actorId, Integer stuId, String stuName, String medicineName, String dose, Instant executeTime, Set<MedicineTime> medicineTime, Integer days, Integer klassId, Integer schoolId, String remark, Instant createdAt) {
        this.actorId = actorId;
        this.stuId = stuId;
        this.stuName = stuName;
        this.medicineName = medicineName;
        this.dose = dose;
        this.executeTime = executeTime;
        this.medicineTime = medicineTime;
        this.days = days;
        this.klassId = klassId;
        this.schoolId = schoolId;
        this.remark = remark;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Integer getCarryActorId() {
        return carryActorId;
    }

    public void setCarryActorId(Integer carryActorId) {
        this.carryActorId = carryActorId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Instant getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Instant executeTime) {
        this.executeTime = executeTime;
    }

    public Set<MedicineTime> getMedicineTime() {
        return medicineTime;
    }

    public void setMedicineTime(Set<MedicineTime> medicineTime) {
        this.medicineTime = medicineTime;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean getIsStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
