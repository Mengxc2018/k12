package cn.k12soft.servo.module.account.domain;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@DynamicUpdate
public class StudentAccountChangeRecord{
    @Id
    @GeneratedValue
    private Integer id;
//    @Column(nullable = false)
    @OneToOne
    private Klass klass;
//    @Column(nullable = false)
    private Integer studentAccountId = 0; // 账号id
    @OneToOne
    private Student student; // 学生
    @Column(nullable = false)
    private Float changeMoney;// 变化的费用
    @Column(nullable = false)
    private Float remainMoney;// 剩余费用
    @OneToOne
    private Actor createdBy;// 操作人
    @Column(nullable = false)
    private Instant createAt;
    @Column(nullable = false)
    private Integer opType;// 来源

    public StudentAccountChangeRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentAccountId() {
        return studentAccountId;
    }

    public void setStudentAccountId(Integer studentAccountId) {
        this.studentAccountId = studentAccountId;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Float getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(Float changeMoney) {
        this.changeMoney = changeMoney;
    }

    public Float getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(Float remainMoney) {
        this.remainMoney = remainMoney;
    }

    public Actor getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Actor createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
