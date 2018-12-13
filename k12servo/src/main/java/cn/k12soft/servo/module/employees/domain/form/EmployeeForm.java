package cn.k12soft.servo.module.employees.domain.form;

import cn.k12soft.servo.module.duty.domain.Duty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

@ApiModel
public class EmployeeForm{

    @ApiModelProperty("档案编号")
    private Integer fileId;

    @ApiModelProperty("当前员工actorId")
    private String actorId;    // 当前员工

    @ApiModelProperty("员工姓名")
    private String name;

    @ApiModelProperty("部门")
    private Integer deptId;

//    @ApiModelProperty("职位")
//    private String post;

    @ApiModelProperty("职务")
    private Integer dutyId;     // 职务Id

    @ApiModelProperty("试用期：单位：月")
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
    @ApiModelProperty("有无毕业证")
    private boolean hasDoploma; // 是否有毕业证
    @ApiModelProperty("有无社保")
    private boolean hasSocial;  // 有无社保

    public Integer getFileId() {
        return fileId;
    }

    public String getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public Integer getDutyId() {
        return dutyId;
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

    public boolean getIsHasDoploma() {
        return hasDoploma;
    }

    public boolean getIsHasSocial() {
        return hasSocial;
    }

    public Integer getProbation() {
        return probation;
    }
}
