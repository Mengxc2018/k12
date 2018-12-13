package cn.k12soft.servo.module.attendanceCount.domain.dto;

import java.util.List;

public class AttendanceCountDTO{

    private Integer actorId;
    private Integer schoolId;
    private List<AttendTimeDTO> attendList;
    private Integer count;          // 统计

    public AttendanceCountDTO(){}

    public AttendanceCountDTO(Integer actorId, Integer schoolId, List<AttendTimeDTO> attendList, Integer count) {
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.attendList = attendList;
        this.count = count;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public List<AttendTimeDTO> getAttendList() {
        return attendList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
