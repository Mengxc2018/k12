package cn.k12soft.servo.module.medicine.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 喂药时间及详情
 */
@Entity
@DynamicUpdate
public class MedicineTime {

    @Id
    @GeneratedValue
    private Long Id;
    private String time;       // 吃药时间
    private boolean isTake;     // 是否服用
    private boolean isStop;     // 是否停药
    private Integer carryActorId;   // 执行人actorid

    public MedicineTime() {
    }

    public MedicineTime(String time, boolean isTake, boolean isStop) {
        this.time = time;
        this.isTake = isTake;
        this.isStop = isStop;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsStop() {
        return isStop;
    }

    public boolean getIsTake() {
        return isTake;
    }

    public void setTake(boolean take) {
        isTake = take;
    }

    public Integer getCarryActorId() {
        return carryActorId;
    }

    public void setCarryActorId(Integer carryActorId) {
        this.carryActorId = carryActorId;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
