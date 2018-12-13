package cn.k12soft.servo.module.schedulingPerson.domian;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
public class SchedulingPerson {

    @Id
    @GeneratedValue
    private long id;
    private Integer actorId;
    private Integer schoolId;
    private String userName;
    @ManyToOne
    @JoinColumn(name="scheduling_id")
    private Scheduling scheduling;
    @JsonIgnore
    private Instant createdAt;
    @JsonIgnore
    private Instant updatedAt;

    public SchedulingPerson(){
    }

    public SchedulingPerson(Integer actorId, Integer schoolId, String userName, Scheduling scheduling, Instant createdAt, Instant updatedAt) {
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.userName = userName;
        this.scheduling = scheduling;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
