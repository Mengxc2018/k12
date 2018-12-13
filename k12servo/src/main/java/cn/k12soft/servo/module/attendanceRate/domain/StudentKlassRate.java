package cn.k12soft.servo.module.attendanceRate.domain;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.School;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * 学校中班级的出勤率
 */
@Entity
@DynamicUpdate
public class StudentKlassRate {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Klass klass;
    @ManyToOne(cascade = CascadeType.ALL)
    private School school;
    private String january = "0.00";     // 一月
    private String february = "0.00";    // 二月
    private String march = "0.00";       // 三月
    private String april = "0.00";       // 四月
    private String may = "0.00";         // 五月
    private String june = "0.00";        // 六月
    private String july = "0.00";        // 七月
    private String auguest = "0.00";     // 八月
    private String september = "0.00";   // 九月
    private String october = "0.00";     // 十月
    private String november = "0.00";    // 十一月
    private String december = "0.00";    // 十二月
    private Instant createdAt;  // 创建时间

    public StudentKlassRate(){}

    public StudentKlassRate(Klass klass, School school, String january, String february, String march, String april, String may, String june, String july, String auguest, String september, String october, String november, String december, Instant createdAt) {
        this.klass = klass;
        this.school = school;
        this.january = january;
        this.february = february;
        this.march = march;
        this.april = april;
        this.may = may;
        this.june = june;
        this.july = july;
        this.auguest = auguest;
        this.september = september;
        this.october = october;
        this.november = november;
        this.december = december;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getJanuary() {
        return january;
    }

    public void setJanuary(String january) {
        this.january = january;
    }

    public String getFebruary() {
        return february;
    }

    public void setFebruary(String february) {
        this.february = february;
    }

    public String getMarch() {
        return march;
    }

    public void setMarch(String march) {
        this.march = march;
    }

    public String getApril() {
        return april;
    }

    public void setApril(String april) {
        this.april = april;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getJune() {
        return june;
    }

    public void setJune(String june) {
        this.june = june;
    }

    public String getJuly() {
        return july;
    }

    public void setJuly(String july) {
        this.july = july;
    }

    public String getAuguest() {
        return auguest;
    }

    public void setAuguest(String auguest) {
        this.auguest = auguest;
    }

    public String getSeptember() {
        return september;
    }

    public void setSeptember(String september) {
        this.september = september;
    }

    public String getOctober() {
        return october;
    }

    public void setOctober(String october) {
        this.october = october;
    }

    public String getNovember() {
        return november;
    }

    public void setNovember(String november) {
        this.november = november;
    }

    public String getDecember() {
        return december;
    }

    public void setDecember(String december) {
        this.december = december;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
