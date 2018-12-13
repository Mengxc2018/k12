package cn.k12soft.servo.module.revenue.domain;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.SchoolEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 支出类别
 */
@Entity
@DynamicUpdate
public class PayoutMainType extends SchoolEntity{
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;

    public PayoutMainType(){}

    public PayoutMainType(Integer schoolId){super(schoolId);}

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
}
