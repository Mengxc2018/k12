package cn.k12soft.servo.module.employees.domain.form;

import java.time.Instant;

public class EmployeeForm{

    private Integer dutyId;
    private String actorId;
    private Instant joinAt;     // 入职时间
    private boolean isOfficial; // 是否转正
    private boolean isGraduate; // 是否毕业
    private boolean hasDiploma; // 是否有毕业证

    public EmployeeForm(Integer dutyId, String actorId, Instant joinAt, boolean isOfficial, boolean isGraduate, boolean hasDiploma) {
        this.dutyId = dutyId;
        this.actorId = actorId;
        this.joinAt = joinAt;
        this.isOfficial = isOfficial;
        this.isGraduate = isGraduate;
        this.hasDiploma = hasDiploma;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public String getActorId() {
        return actorId;
    }

    public Instant getJoinAt() {
        return joinAt;
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public boolean isGraduate() {
        return isGraduate;
    }

    public boolean isHasDiploma() {
        return hasDiploma;
    }
}
