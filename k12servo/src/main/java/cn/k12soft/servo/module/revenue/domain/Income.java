package cn.k12soft.servo.module.revenue.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Created by liubing on 2018/3/20
 * 收入
 */
@Entity
@DynamicUpdate
public class Income extends SchoolEntity {
    @Id
    @GeneratedValue
    private Integer id;
    //@Column(nullable = false)
    private Integer studentChargeId;// 学生收费计划id
    @Column(nullable = false)
    private Integer src; // 费用来源
    @Column(nullable = false)
    private Integer klassId;
    @Column(nullable = false)
    private Integer klassType;
    @Column(nullable = false)
    private String klassName;
    @Column(nullable = false)
    private Integer expenseId;
    @Column(nullable = false)
    private String names;
    @Column(nullable = false)
    private Float money;
    @Column(nullable = false)
    private Instant createAt;
    @Column(nullable = false)
    private Integer theYearMonth;

    public Income() {
    }

    public Income(Integer schoolId) {
        super(schoolId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentChargeId() {
        return studentChargeId;
    }

    public void setStudentChargeId(Integer studentChargeId) {
        this.studentChargeId = studentChargeId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public Integer getKlassType() {
        return klassType;
    }

    public void setKlassType(Integer klassType) {
        this.klassType = klassType;
    }

    public Integer getSrc() {
        return src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public String getKlassName() {
        return klassName;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public Integer getTheYearMonth() {
        return theYearMonth;
    }

    public void setTheYearMonth(Integer theYearMonth) {
        this.theYearMonth = theYearMonth;
    }
}
