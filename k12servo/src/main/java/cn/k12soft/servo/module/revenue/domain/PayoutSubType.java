package cn.k12soft.servo.module.revenue.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 支出明细(子类别)
 */
@Entity
@DynamicUpdate
public class PayoutSubType extends SchoolEntity{
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private PayoutMainType payoutMainType; //类别

    public PayoutSubType(){}

    public PayoutSubType(Integer schoolId){super(schoolId);}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PayoutMainType getPayoutMainType() {
        return payoutMainType;
    }

    public void setPayoutMainType(PayoutMainType payoutMainType) {
        this.payoutMainType = payoutMainType;
    }
}
