package cn.k12soft.servo.module.attendanceRate.domain.dto;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.School;

import java.time.Instant;

public class StudentKlassRateDTO {
    private Long id;
    private Klass klass;
    private School school;
    private String january;     // 一月
    private String february;    // 二月
    private String march;       // 三月
    private String april;       // 四月
    private String may;         // 五月
    private String june;        // 六月
    private String july;        // 七月
    private String auguest;     // 八月
    private String september;   // 九月
    private String october;     // 十月
    private String november;    // 十一月
    private String december;    // 十二月
    private Instant createdAt;  // 创建时间

    public StudentKlassRateDTO(Long id,Klass klass, School school, String january, String february, String march, String april, String may, String june, String july, String auguest, String september, String october, String november, String december, Instant createdAt) {
        this.id = id;
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

    public Klass getKlass() {
        return klass;
    }

    public School getSchool() {
        return school;
    }

    public String getJanuary() {
        return january;
    }

    public String getFebruary() {
        return february;
    }

    public String getMarch() {
        return march;
    }

    public String getApril() {
        return april;
    }

    public String getMay() {
        return may;
    }

    public String getJune() {
        return june;
    }

    public String getJuly() {
        return july;
    }

    public String getAuguest() {
        return auguest;
    }

    public String getSeptember() {
        return september;
    }

    public String getOctober() {
        return october;
    }

    public String getNovember() {
        return november;
    }

    public String getDecember() {
        return december;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
