package cn.k12soft.servo.module.activiti.activitisTypes.domain.dto;

public class ActivitiTypeDTO {

    private String name;    // 类别名
    private Long typeNo;    // 标识
    private Integer schoolId;

    public ActivitiTypeDTO(String name, Long typeNo, Integer schoolId) {
        this.name = name;
        this.typeNo = typeNo;
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public Long getTypeNo() {
        return typeNo;
    }

    public Integer getSchoolId() {
        return schoolId;
    }
}
