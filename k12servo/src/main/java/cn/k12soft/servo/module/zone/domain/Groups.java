package cn.k12soft.servo.module.zone.domain;

import cn.k12soft.servo.module.department.domain.Dept;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

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
    @Column
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Dept> department;

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

    public Set<Dept> getDepartment() {
        return department;
    }

    public void setDepartment(Set<Dept> department) {
        this.department = department;
    }
}
