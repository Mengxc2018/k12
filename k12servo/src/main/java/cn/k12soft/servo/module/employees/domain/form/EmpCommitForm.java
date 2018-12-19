package cn.k12soft.servo.module.employees.domain.form;

import cn.k12soft.servo.module.duty.domain.Duty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.ManyToOne;
import java.time.Instant;

public class EmpCommitForm {

    @ApiModelProperty("当前员工actorId")
    private Integer actorId;    // 当前员工

    @ApiModelProperty("员工姓名")
    private String name;

    //    @OneToOne
    @ApiModelProperty("部门")
    private Integer deptId;

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

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否毕业")
    private boolean isGraduate; // 是否毕业
    @ApiModelProperty("是否有毕业证")
    private boolean isHasDiploma; // 是否有毕业证

    @ManyToOne
    private Duty duty;     // 职务Id

    @ApiModelProperty("该员工的上级id")
    private Integer parentActorId;    // 该员工的上级id

    public EmpCommitForm(Integer actorId, String name, Integer deptId, String idCard, String sex, String mobile, String emergencyContact, String emergencyContactMobile, String nativePlace, String nation, String politicsStatus, String isMarry, String hjAddress, String address, String education, String graduateSchool, String specialty, String cgfns, String remark, boolean isGraduate, boolean isHasDiploma, Duty duty, Integer parentActorId) {
        this.actorId = actorId;
        this.name = name;
        this.deptId = deptId;
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
        this.remark = remark;
        this.isGraduate = isGraduate;
        this.isHasDiploma = isHasDiploma;
        this.duty = duty;
        this.parentActorId = parentActorId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public Integer getDeptId() {
        return deptId;
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

    public String getRemark() {
        return remark;
    }

    public boolean getIsGraduate() {
        return isGraduate;
    }

    public boolean getIsHasDiploma() {
        return isHasDiploma;
    }

    public Duty getDuty() {
        return duty;
    }

    public Integer getParentActorId() {
        return parentActorId;
    }
}
