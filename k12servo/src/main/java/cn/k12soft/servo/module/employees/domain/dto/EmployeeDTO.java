package cn.k12soft.servo.module.employees.domain.dto;

import cn.k12soft.servo.module.department.domain.Dept;
import cn.k12soft.servo.module.duty.domain.Duty;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

public class EmployeeDTO {

    private Long id;

    @ApiModelProperty("档案编号")
    private Integer fileId;

    @ApiModelProperty("当前员工actorId")
    private Integer actorId;    // 当前员工

    @ApiModelProperty("员工姓名")
    private String userName;

    @ApiModelProperty("员工工号")
    private Integer actorNum;   // 员工工号

    @ApiModelProperty("部门")
    private Dept dept;

    @ApiModelProperty("试用时长、单位：月")
    private Integer probation;

    @ApiModelProperty("试用工资")
    private Float salaryProbationer;


    @ApiModelProperty("转正工资")
    private Float salary;

    @ApiModelProperty("入职日期")
    private Instant dateJoinAt;

    @ApiModelProperty("转正日期")
    private Instant dateOfficialAt;

    @ApiModelProperty("登记日期")
    private Instant dateRegisterAt;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("性别：1、男性 2、女性 3、未知")
    private String sex;

    @ApiModelProperty("电话号码")
    private String mobile;

    @ApiModelProperty("紧急联系人")
    private String emergencyContact;

    @ApiModelProperty("紧急联系人电话")
    private String emergencyContactMobile;

    @ApiModelProperty("籍贯")
    private String nativePlace;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("政治面貌")
    private String politicsStatus;

    @ApiModelProperty("婚姻状况:0未婚，1已婚，2未知")
    private String isMarry;

    @ApiModelProperty("户籍地址")
    private String hjAddress;

    @ApiModelProperty("现住址")
    private String address;

    @ApiModelProperty("学历")
    private String education;

    @ApiModelProperty("毕业院校")
    private String graduateSchool;

    @ApiModelProperty("专业")
    private String specialty;

    @ApiModelProperty("其他资格证书")
    private String cgfns;

    @ApiModelProperty("用工形式")
    private String useForm;

    @ApiModelProperty("是否签订劳动合同")
    private boolean isContract;

    @ApiModelProperty("合同起始日期")
    private Instant contractDateStart;

    @ApiModelProperty("合同截止日期")
    private Instant contractDateEnd;

    @ApiModelProperty("劳动合同续签提醒")
    private Instant renewRemind;

    @ApiModelProperty("续签情况")
    private boolean isRenew;

    @ApiModelProperty("续签起始日期")
    private Instant renewDateStart;

    @ApiModelProperty("续签截止日期")
    private Instant renewDateEnd;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否毕业")
    private boolean isGraduate; // 是否毕业
    @ApiModelProperty("是否有毕业证")
    private boolean isHasDiploma; // 是否有毕业证
    @ApiModelProperty("是否转正")
    private boolean isOfficial;
    @ApiModelProperty("是否离职")
    private boolean isLeave;    // 是否离职
    @ApiModelProperty("离职时间")
    private Instant leaveAt;    // 离职时间
    @ApiModelProperty("有无社保")
    private boolean hasSocial;  // 有无社保

    private Duty duty;     // 职务Id

    @ApiModelProperty("该员工的上级id")
    private Integer parentActorId;    // 该员工的上级id

    @ApiModelProperty("加班时间")
    private String overtime;    // 加班时间
    @ApiModelProperty("调休时间")
    private String rest;        // 调休时间
    @ApiModelProperty("年假时间")
    private String annual;      // 年假时间
    @ApiModelProperty("病假时间")
    private String sick;        // 病假时间
    @ApiModelProperty("产假")
    private String barth;       // 产假
    @ApiModelProperty("陪产假")
    private String barthWith;   // 陪产假
    @ApiModelProperty("婚假")
    private String marry;       // 婚假
    @ApiModelProperty("丧假")
    private String funeral;     // 丧假


    private boolean isShow;     // 是否显示
    private Integer schoolId;   // 学校Id
    private Instant createdAt;  // 创建时间

    private String masterName;  // 上级姓名
    private String klassId;
    private String klassName;
    private String courseName;
    private String courseId;

    public EmployeeDTO(){}

    public EmployeeDTO(Integer actorId, String userName, Integer schoolId, String mobile) {
        this.actorId = actorId;
        this.userName = userName;
        this.schoolId = schoolId;
        this.mobile = mobile;
    }

    public EmployeeDTO(Long id, Integer fileId, Integer actorId, String userName, Integer actorNum, Dept dept, Integer probation, Float salaryProbationer, Float salary, Instant dateJoinAt, Instant dateOfficialAt, Instant dateRegisterAt, String idCard, String sex, String mobile, String emergencyContact, String emergencyContactMobile, String nativePlace, String nation, String politicsStatus, String isMarry, String hjAddress, String address, String education, String graduateSchool, String specialty, String cgfns, String useForm, boolean isContract, Instant contractDateStart, Instant contractDateEnd, Instant renewRemind, boolean isRenew, Instant renewDateStart, Instant renewDateEnd, String remark, boolean isGraduate, boolean isHasDiploma, boolean isOfficial, boolean isLeave, Instant leaveAt, boolean hasSocial, Duty duty, Integer parentActorId, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral, boolean isShow, Integer schoolId, Instant createdAt, String masterName, String klassId, String klassName, String courseName, String courseId) {
        this.id = id;
        this.fileId = fileId;
        this.actorId = actorId;
        this.userName = userName;
        this.actorNum = actorNum;
        this.dept = dept;
        this.probation = probation;
        this.salaryProbationer = salaryProbationer;
        this.salary = salary;
        this.dateJoinAt = dateJoinAt;
        this.dateOfficialAt = dateOfficialAt;
        this.dateRegisterAt = dateRegisterAt;
        this.idCard = idCard;
        this.sex = sex;
        this.mobile = mobile;
        this.emergencyContact = emergencyContact;
        this.emergencyContactMobile = emergencyContactMobile;
        this.nativePlace = nativePlace;
        this.nation = nation;
        this.politicsStatus = politicsStatus;
        this.isMarry = isMarry;
        this.hjAddress = hjAddress;
        this.address = address;
        this.education = education;
        this.graduateSchool = graduateSchool;
        this.specialty = specialty;
        this.cgfns = cgfns;
        this.useForm = useForm;
        this.isContract = isContract;
        this.contractDateStart = contractDateStart;
        this.contractDateEnd = contractDateEnd;
        this.renewRemind = renewRemind;
        this.isRenew = isRenew;
        this.renewDateStart = renewDateStart;
        this.renewDateEnd = renewDateEnd;
        this.remark = remark;
        this.isGraduate = isGraduate;
        this.isHasDiploma = isHasDiploma;
        this.isOfficial = isOfficial;
        this.isLeave = isLeave;
        this.leaveAt = leaveAt;
        this.hasSocial = hasSocial;
        this.duty = duty;
        this.parentActorId = parentActorId;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
        this.isShow = isShow;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.masterName = masterName;
        this.klassId = klassId;
        this.klassName = klassName;
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getActorNum() {
        return actorNum;
    }

    public Dept getDept() {
        return dept;
    }

    public Integer getProbation() {
        return probation;
    }

    public Float getSalaryProbationer() {
        return salaryProbationer;
    }

    public Float getSalary() {
        return salary;
    }

    public Instant getDateJoinAt() {
        return dateJoinAt;
    }

    public Instant getDateOfficialAt() {
        return dateOfficialAt;
    }

    public Instant getDateRegisterAt() {
        return dateRegisterAt;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getSex() {
        return sex;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getEmergencyContactMobile() {
        return emergencyContactMobile;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public String getIsMarry() {
        return isMarry;
    }

    public String getHjAddress() {
        return hjAddress;
    }

    public String getAddress() {
        return address;
    }

    public String getEducation() {
        return education;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getCgfns() {
        return cgfns;
    }

    public String getUseForm() {
        return useForm;
    }

    public boolean getIsContract() {
        return isContract;
    }

    public Instant getContractDateStart() {
        return contractDateStart;
    }

    public Instant getContractDateEnd() {
        return contractDateEnd;
    }

    public Instant getRenewRemind() {
        return renewRemind;
    }

    public boolean getIsRenew() {
        return isRenew;
    }

    public Instant getRenewDateStart() {
        return renewDateStart;
    }

    public Instant getRenewDateEnd() {
        return renewDateEnd;
    }

    public String getRemark() {
        return remark;
    }

    public boolean getIsGraduate() {
        return isGraduate;
    }

    public boolean getIsHasDiploma() {
        return isHasDiploma;
    }

    public boolean getIsOfficial() {
        return isOfficial;
    }

    public boolean getIsLeave() {
        return isLeave;
    }

    public Instant getLeaveAt() {
        return leaveAt;
    }

    public boolean getIsHasSocial() {
        return hasSocial;
    }

    public Duty getDuty() {
        return duty;
    }

    public Integer getParentActorId() {
        return parentActorId;
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

    public boolean getIsShow() {
        return isShow;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getKlassId() {
        return klassId;
    }

    public String getKlassName() {
        return klassName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }
}
