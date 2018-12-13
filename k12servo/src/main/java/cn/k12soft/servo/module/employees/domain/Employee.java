package cn.k12soft.servo.module.employees.domain;

import cn.k12soft.servo.module.duty.domain.Duty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@ApiModel
@DynamicUpdate
public class Employee{

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty("档案编号")
    private Integer fileId;

    @ApiModelProperty("当前员工actorId")
    private Integer actorId;    // 当前员工

    @ApiModelProperty("员工姓名")
    private String name;

    @ApiModelProperty("员工工号")
    private Integer actorNum;   // 员工工号

//    @OneToOne
    @ApiModelProperty("部门")
    private Integer deptId;

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

    @ManyToOne
    private Duty duty;     // 职务Id

    @ApiModelProperty("该员工的上级id")
    private Integer parentActorId;    // 该员工的上级id

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private EmployeeBasic employeeBasic;

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

    public Employee() {
    }

    public Employee(Duty duty, Integer actorId, Integer actorNum, Integer schoolId, boolean isShow, boolean hasSocial, Instant dateJoinAt, Instant dateOfficialAt, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral, Instant createdAt) {
        this.duty = duty;
        this.actorId = actorId;
        this.actorNum = actorNum;
        this.schoolId = schoolId;
        this.isShow = isShow;
        this.hasSocial = hasSocial;
        this.dateJoinAt = dateJoinAt;
        this.dateOfficialAt = dateOfficialAt;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
        this.createdAt = createdAt;
    }

    public Employee(Duty duty, Integer actorId, Integer actorNum, Integer schoolId , Integer probation, boolean isShow, boolean hasSocial, Instant dateJoinAt,
                    Instant dateOfficialAt, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral,
                    boolean isOfficial, boolean isGraduate, boolean isHasDiploma, Instant createdAt) {
        this.duty = duty;
        this.actorId = actorId;
        this.actorNum = actorNum;
        this.schoolId = schoolId;
        this.probation = probation;
        this.isShow = isShow;
        this.hasSocial = hasSocial;
        this.dateJoinAt = dateJoinAt;
        this.dateOfficialAt = dateOfficialAt;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
        this.isOfficial = isOfficial;
        this.isGraduate = isGraduate;
        this.isHasDiploma = isHasDiploma;
        this.createdAt = createdAt;
    }

    public Employee(Long id, Duty duty, Integer actorId, Integer actorNum, Integer schoolId, boolean isShow, boolean hasSocial, Instant dateJoinAt, Instant dateOfficialAt, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral, Instant createdAt) {
        this.id = id;
        this.duty = duty;
        this.actorId = actorId;
        this.actorNum = actorNum;
        this.schoolId = schoolId;
        this.isShow = isShow;
        this.hasSocial = hasSocial;
        this.dateJoinAt = getDateJoinAt();
        this.dateOfficialAt = dateOfficialAt;
        this.overtime = overtime;
        this.rest = rest;
        this.annual = annual;
        this.sick = sick;
        this.barth = barth;
        this.barthWith = barthWith;
        this.marry = marry;
        this.funeral = funeral;
        this.createdAt = createdAt;
    }

    public Employee(Long id, Integer fileId, Integer actorId, String name, Integer actorNum, Integer deptId, Integer probation, Float salaryProbationer, Float salary, Instant dateJoinAt, Instant dateOfficialAt, Instant dateRegisterAt, String idCard, String sex, String mobile, String emergencyContact, String emergencyContactMobile, String nativePlace, String nation, String politicsStatus, String isMarry, String hjAddress, String address, String education, String graduateSchool, String specialty, String cgfns, String useForm, boolean isContract, Instant contractDateStart, Instant contractDateEnd, Instant renewRemind, boolean isRenew, Instant renewDateStart, Instant renewDateEnd, String remark, boolean isGraduate, boolean hasDoploma, boolean hasSocial, Duty duty, String overtime, String rest, String annual, String sick, String barth, String barthWith, String marry, String funeral, boolean isShow, Integer schoolId, Instant createdAt) {
        this.id = id;
        this.fileId = fileId;
        this.actorId = actorId;
        this.name = name;
        this.actorNum = actorNum;
        this.deptId = deptId;
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
        this.isHasDiploma = hasDoploma;
        this.hasSocial = hasSocial;
        this.duty = duty;
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Integer getActorNum() {
        return actorNum;
    }

    public void setActorNum(Integer actorNum) {
        this.actorNum = actorNum;
    }

    public Integer getParentActorId() {
        return parentActorId;
    }

    public void setParentActorId(Integer parentActorId) {
        this.parentActorId = parentActorId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getSick() {
        return sick;
    }

    public void setSick(String sick) {
        this.sick = sick;
    }

    public String getBarth() {
        return barth;
    }

    public void setBarth(String barth) {
        this.barth = barth;
    }

    public String getBarthWith() {
        return barthWith;
    }

    public void setBarthWith(String barthWith) {
        this.barthWith = barthWith;
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public String getFuneral() {
        return funeral;
    }

    public void setFuneral(String funeral) {
        this.funeral = funeral;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Float getSalaryProbationer() {
        return salaryProbationer;
    }

    public void setSalaryProbationer(Float salaryProbationer) {
        this.salaryProbationer = salaryProbationer;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Instant getDateJoinAt() {
        return dateJoinAt;
    }

    public void setDateJoinAt(Instant dateJoinAt) {
        this.dateJoinAt = dateJoinAt;
    }

    public Instant getDateOfficialAt() {
        return dateOfficialAt;
    }

    public void setDateOfficialAt(Instant dateOfficialAt) {
        this.dateOfficialAt = dateOfficialAt;
    }

    public Instant getDateRegisterAt() {
        return dateRegisterAt;
    }

    public void setDateRegisterAt(Instant dateRegisterAt) {
        this.dateRegisterAt = dateRegisterAt;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactMobile() {
        return emergencyContactMobile;
    }

    public void setEmergencyContactMobile(String emergencyContactMobile) {
        this.emergencyContactMobile = emergencyContactMobile;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getIsMarry() {
        return isMarry;
    }

    public void setIsMarry(String isMarry) {
        this.isMarry = isMarry;
    }

    public String getHjAddress() {
        return hjAddress;
    }

    public void setHjAddress(String hjAddress) {
        this.hjAddress = hjAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCgfns() {
        return cgfns;
    }

    public void setCgfns(String cgfns) {
        this.cgfns = cgfns;
    }

    public String getUseForm() {
        return useForm;
    }

    public void setUseForm(String useForm) {
        this.useForm = useForm;
    }

    public boolean getIsContract() {
        return isContract;
    }

    public void setContract(boolean contract) {
        isContract = contract;
    }

    public Instant getContractDateStart() {
        return contractDateStart;
    }

    public void setContractDateStart(Instant contractDateStart) {
        this.contractDateStart = contractDateStart;
    }

    public Instant getContractDateEnd() {
        return contractDateEnd;
    }

    public void setContractDateEnd(Instant contractDateEnd) {
        this.contractDateEnd = contractDateEnd;
    }

    public Instant getRenewRemind() {
        return renewRemind;
    }

    public void setRenewRemind(Instant renewRemind) {
        this.renewRemind = renewRemind;
    }

    public boolean getIsRenew() {
        return isRenew;
    }

    public void setRenew(boolean renew) {
        isRenew = renew;
    }

    public Instant getRenewDateStart() {
        return renewDateStart;
    }

    public void setRenewDateStart(Instant renewDateStart) {
        this.renewDateStart = renewDateStart;
    }

    public Instant getRenewDateEnd() {
        return renewDateEnd;
    }

    public void setRenewDateEnd(Instant renewDateEnd) {
        this.renewDateEnd = renewDateEnd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean getIsGraduate() {
        return isGraduate;
    }

    public void setGraduate(boolean graduate) {
        isGraduate = graduate;
    }

    public Integer getProbation() {
        return probation;
    }

    public void setProbation(Integer probation) {
        this.probation = probation;
    }

    public boolean getIsHasDiploma() {
        return isHasDiploma;
    }

    public void setHasDiploma(boolean hasDiploma) {
        isHasDiploma = hasDiploma;
    }

    public boolean getIsOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    public boolean getIsLeave() {
        return isLeave;
    }

    public void setLeave(boolean leave) {
        isLeave = leave;
    }

    public Instant getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(Instant leaveAt) {
        this.leaveAt = leaveAt;
    }

    public boolean getIsHasSocial() {
        return hasSocial;
    }

    public void setHasSocial(boolean hasSocial) {
        this.hasSocial = hasSocial;
    }

    public boolean getIsShow() {
        return isShow;
    }
}

