package cn.k12soft.servo.module.AttendanceTeacher.VUtils;

import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;

/**
 * 判断某一天是否满勤
 * 返回不满勤的时长（一天缺勤多长时间（秒））
 * 不满勤的打卡信息及补签信息
 */
public class IsAttendacne {

    private boolean isAll;  // 是否满勤
    private Long time;      // 一天内不满勤缺少的时间 单位：秒
    private AttendTeacDTO attendTeacDTO;    // 打卡信息

    public IsAttendacne(){}

    public IsAttendacne(boolean isAll, Long time, AttendTeacDTO attendTeacDTO) {
        this.isAll = isAll;
        this.time = time;
        this.attendTeacDTO = attendTeacDTO;
    }

    public boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(boolean isAll) {
        isAll = isAll;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public AttendTeacDTO getAttendTeacDTO() {
        return attendTeacDTO;
    }

    public void setAttendTeacDTO(AttendTeacDTO attendTeacDTO) {
        this.attendTeacDTO = attendTeacDTO;
    }
}
