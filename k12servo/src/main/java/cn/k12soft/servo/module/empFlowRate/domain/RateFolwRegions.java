package cn.k12soft.servo.module.empFlowRate.domain;

import cn.k12soft.servo.module.zone.domain.Regions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@DynamicUpdate
public class RateFolwRegions {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Regions regions;
    private Integer groupId;
    @JsonIgnore
    private String status;  // 离职、入职
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
    private Instant updateAt;   // 更新时间

    public RateFolwRegions(){

    }

    public RateFolwRegions(String status, Regions regions, int groupId, Instant createdAt) {
        this.status = status;
        this.regions = regions;
        this.groupId = groupId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Regions getRegions() {
        return regions;
    }

    public void setRegions(Regions regions) {
        this.regions = regions;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }
}
