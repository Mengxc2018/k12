package cn.k12soft.servo.module.duty.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

public class DutyDTO {
    private Integer id;
    private String name;
    private Integer schoolId;
    private VacationTeacherUtil.TRUE_FALSE isSubstratum;

    public DutyDTO(){}

    public DutyDTO(Integer id, String name, Integer schoolId, VacationTeacherUtil.TRUE_FALSE isSubstratum) {
        this.id = id;
        this.name = name;
        this.schoolId = schoolId;
        this.isSubstratum = isSubstratum;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public VacationTeacherUtil.TRUE_FALSE getIsSubstratum() {
        return isSubstratum;
    }

    public VacationTeacherUtil.TRUE_FALSE isSubstratum() {
        return isSubstratum;
    }
}
