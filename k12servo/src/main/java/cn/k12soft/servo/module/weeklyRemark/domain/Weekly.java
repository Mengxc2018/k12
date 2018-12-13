package cn.k12soft.servo.module.weeklyRemark.domain;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@DynamicUpdate
@ApiModel
public class Weekly {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Student student;

    @ApiModelProperty(value = "时间周期，例：2018/04/16--04/20")
    private String datePeriod;

    @ApiModelProperty(value = "教师评语")
    private String TContext;

    @ApiModelProperty(value = "家长评语")
    private String PContext;

    @ApiModelProperty("家长是否提交，默认false")
    private boolean pStatus = false;

    @ApiModelProperty(value = "情绪")
    private Integer emotion;

    @ApiModelProperty(value = "进餐")
    private Integer dine;

    @ApiModelProperty(value = "睡眠")
    private Integer seleep;

    @ApiModelProperty(value = "适应环境")
    private Integer environment;

    @ApiModelProperty(value = "伙伴相处")
    private Integer partner;

    @ApiModelProperty(value = "环境卫生")
    private Integer sanitation;

    @ApiModelProperty(value = "身体状况")
    private Integer health;

    @ApiModelProperty(value = "自理能力")
    private Integer self;

    private Instant createdAt;
    @OneToOne
    private Klass klass;

    private Integer schoolId;

    public Weekly() {
    }

    public Weekly(Student student, String datePeriod, String TContext, String PContext, boolean pStatus, Integer emotion, Integer dine, Integer seleep, Integer environment, Integer partner, Integer sanitation, Integer health, Integer self, Instant createdAt, Klass klass, Integer schoolId) {
        this.student = student;
        this.datePeriod = datePeriod;
        this.TContext = TContext;
        this.PContext = PContext;
        this.pStatus = pStatus;
        this.emotion = emotion;
        this.dine = dine;
        this.seleep = seleep;
        this.environment = environment;
        this.partner = partner;
        this.sanitation = sanitation;
        this.health = health;
        this.self = self;
        this.createdAt = createdAt;
        this.klass = klass;
        this.schoolId = schoolId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDatePeriod() {
        return datePeriod;
    }

    public void setDatePeriod(String datePeriod) {
        this.datePeriod = datePeriod;
    }

    public String getTContext() {
        return TContext;
    }

    public void setTContext(String TContext) {
        this.TContext = TContext;
    }

    public String getPContext() {
        return PContext;
    }

    public void setPContext(String PContext) {
        this.PContext = PContext;
    }

    public boolean getIspStatus() {
        return pStatus;
    }

    public void setpStatus(boolean pStatus) {
        this.pStatus = pStatus;
    }

    public Integer getEmotion() {
        return emotion;
    }

    public void setEmotion(Integer emotion) {
        this.emotion = emotion;
    }

    public Integer getDine() {
        return dine;
    }

    public void setDine(Integer dine) {
        this.dine = dine;
    }

    public Integer getSeleep() {
        return seleep;
    }

    public void setSeleep(Integer seleep) {
        this.seleep = seleep;
    }

    public Integer getEnvironment() {
        return environment;
    }

    public void setEnvironment(Integer environment) {
        this.environment = environment;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public Integer getSanitation() {
        return sanitation;
    }

    public void setSanitation(Integer sanitation) {
        this.sanitation = sanitation;
    }
    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getSelf() {
        return self;
    }

    public void setSelf(Integer self) {
        this.self = self;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
   }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

}
