package cn.k12soft.servo.module.zone.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * 集团
 * Created by liubing on 2018/7/24
 */
@Entity
@DynamicUpdate
public class Groups {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private Instant createdAt;

    public Groups(){}

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
