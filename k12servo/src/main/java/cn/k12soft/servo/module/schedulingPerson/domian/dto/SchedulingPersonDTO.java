package cn.k12soft.servo.module.schedulingPerson.domian.dto;

import cn.k12soft.servo.module.scheduling.domain.Scheduling;

import java.time.Instant;

public class SchedulingPersonDTO {

    private Long id;
    private Integer actorId;
    private String username;
    private Scheduling schedulingId;
    private Instant createdAt;
    private Instant updatedAt;

    public SchedulingPersonDTO(){}

    public SchedulingPersonDTO(Long id, Integer actorId, String userName, Scheduling schedulingId, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.actorId = actorId;
        this.username = userName;
        this.schedulingId = schedulingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Scheduling getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Scheduling schedulingId) {
        this.schedulingId = schedulingId;
    }

    public void setId(Long id) {
        this.id = id;
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
