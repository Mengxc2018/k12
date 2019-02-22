package cn.k12soft.servo.module.employees.domain.dto;

import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.employees.domain.EmployeeBasic;

public class EmployeeDTO {

    private Long id;
    private Duty duty;
    private Integer actorId;    // 当前员工
    private String userName;    // 员工姓名
    private Integer actorNum;   // 员工编号
    private String mobile;     // 员工手机号
    private Integer parentActorId;    // 该员工的上级id
    private String masterName;  // 该员工上级姓名
    private Integer schoolId;   // 学校Id

    private String klassName;   // 班级信息
    private String klassId;    //
    private String courseName;  // 课程名称
    private String courseId;

    private EmployeeBasic employeeBasic;
    private boolean isShow;     // 是否显示

    private String overtime;    // 加班时间
    private String rest;  // 调休时间
    private String annual;      // 年假时间
    private String sick;        // 病假时间
    private String barth;       // 产假
    private String barthWith;   // 陪产假
    private String marry;       // 婚假
    private String funeral;     // 丧假

    public EmployeeDTO(){}

    public EmployeeDTO(Integer actorId, String userName, Integer schoolId, String mobile) {
        this.actorId = actorId;
        this.userName = userName;
        this.schoolId = schoolId;
        this.mobile = mobile;
    }

    public EmployeeDTO(Long id, Duty duty, Integer actorId, String userName, Integer actorNum, String mobile, Integer parentActorId, String masterName, Integer schoolId, EmployeeBasic employeeBasic, String klassName, String klassId, String courseName, String courseId, boolean isShow, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral) {
        this.id = id;
        this.duty = duty;
        this.actorId = actorId;
        this.userName = userName;
        this.actorNum = actorNum;
        this.mobile = mobile;
        this.parentActorId = parentActorId;
        this.masterName = masterName;
        this.schoolId = schoolId;
        this.employeeBasic = employeeBasic;
        this.klassName = klassName;
        this.klassId = klassId;
        this.courseName = courseName;
        this.courseId = courseId;
        this.isShow = isShow;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
    }

    public Long getId() {
        return id;
    }

    public Duty getDuty() {
        return duty;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getActorNum() {
        return actorNum;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getParentActorId() {
        return parentActorId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public boolean isShow() {
        return isShow;
    }

    public EmployeeBasic getEmployeeBasic() {
        return employeeBasic;
    }

    public String getOvertime() {
        return overtime;
    }

    public String getRest() {
        return rest;
    }

    public String getAnnual() {
        return annual;
    }

    public String getSick() {
        return sick;
    }

    public String getBarth() {
        return barth;
    }

    public String getBarthWith() {
        return barthWith;
    }

    public String getMarry() {
        return marry;
    }

    public String getFuneral() {
        return funeral;
    }

    public String getUserName() {
        return userName;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getKlassName() {
        return klassName;
    }

    public String getKlassId() {
        return klassId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }
}
