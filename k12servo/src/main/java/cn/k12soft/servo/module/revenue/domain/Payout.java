package cn.k12soft.servo.module.revenue.domain;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.SchoolEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.time.Instant;

/**
 * 支出
 */
@Entity
@DynamicUpdate
public class Payout extends SchoolEntity{
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private PayoutSubType payoutSubType;
    @Column(nullable = false)
    private Float money;
    @ManyToOne
    private Actor createdBy;
    @Column(nullable = false)
    private Instant createAt;
    @Column(nullable = false)
    private Integer theYearMonth;

    public Payout(){};

    public Payout(Integer schoolId){super(schoolId);}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PayoutSubType getPayoutSubType() {
        return payoutSubType;
    }

    public void setPayoutSubType(PayoutSubType payoutSubType) {
        this.payoutSubType = payoutSubType;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Actor getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Actor createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Integer getTheYearMonth() {
        return theYearMonth;
    }

    public void setTheYearMonth(Integer theYearMonth) {
        this.theYearMonth = theYearMonth;
    }
}
