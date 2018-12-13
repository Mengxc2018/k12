package cn.k12soft.servo.module.department.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 部门表
 */
@Entity(name = "departments")
@DynamicUpdate
public class Dept {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @JsonIgnore
    private String activitiId;
    private Instant createdAt;

    public Dept(){

    }

    public Dept(String name, String activitiId, Instant createdAt) {
        this.name = name;
        this.activitiId = activitiId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivitiId() {
        return activitiId;
    }

    public void setActivitiId(String activitiId) {
        this.activitiId = activitiId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

