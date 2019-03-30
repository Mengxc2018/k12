package cn.k12soft.servo.module.warehouse.warehouse.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 仓库物品汇总
 */
@Entity
@DynamicUpdate
public class Warehouse {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;    // 商品名称
    private String isbn;    // 商品isbn码、条形码
    @NotNull
    private String spec;    // 商品规格
    @NotNull
    private Float price;    // 单价
    private String country; // 国家
    private String company; // 公司
    private String prefix;  // 厂商识别代码
    private String addr;    // 公司地址

    @NotNull
    private Integer schoolId;    // 学校
    @NotNull
    private Instant createdAt;  // 创建时间

    public Warehouse() {
    }

    public Warehouse(String name, String isbn, String spec, Float price, Integer schoolId, Instant createdAt) {
        this.name = name;
        this.isbn = isbn;
        this.spec = spec;
        this.price = price;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
    }

    public Warehouse(String name, String isbn, String spec, Float price, String country, String company, String prefix, String addr, Integer schoolId, Instant createdAt) {
        this.name = name;
        this.isbn = isbn;
        this.spec = spec;
        this.price = price;
        this.country = country;
        this.company = company;
        this.prefix = prefix;
        this.addr = addr;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
