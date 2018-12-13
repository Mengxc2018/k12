package cn.k12soft.servo.module.applyFor.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;

/**
 * 映射applyfor的massageId，
 */
public class MassageDTO {

    private Long id;
    private Integer actorId;
    private Integer schoolId;
    private String userName;    // 请假人姓名
    private VacationTeacherUtil.VACATIONTYPE reason;
    private String desc;
    private String picture;
    private Instant fromDate;
    private Instant toDate;
    private String portrait;
    private Instant createdAt;
    private String vacationTime;
    private VacationTeacherUtil.ISGONE isGone;

    public MassageDTO(Long id, Integer actorId, Integer schoolId, String userName, VacationTeacherUtil.VACATIONTYPE reason, String desc, String picture, Instant fromDate, Instant toDate, String portrait, Instant createdAt, String vacationTime, VacationTeacherUtil.ISGONE isGone) {
        this.id = id;
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.userName = userName;
        this.reason = reason;
        this.desc = desc;
        this.picture = picture;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.portrait = portrait;
        this.createdAt = createdAt;
        this.vacationTime = vacationTime;
        this.isGone = isGone;
    }

    public Long getId() {
        return id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public String getUserName() {
        return userName;
    }

    public VacationTeacherUtil.VACATIONTYPE getReason() {
        return reason;
    }

    public String getDesc() {
        return desc;
    }

    public String getPicture() {
        return picture;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public String getPortrait() {
        return portrait;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getVacationTime() {
        return vacationTime;
    }

    public VacationTeacherUtil.ISGONE getIsGone() {
        return isGone;
    }
}
