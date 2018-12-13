package cn.k12soft.servo.module.supplier.domain;

import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * 供应商信息
 */
@Entity
@DynamicUpdate
@ApiModel
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ApiModelProperty("厂商名")
    private String name;

    @NotNull
    @ApiModelProperty("联系人")
    private String contacts;

    @NotNull
    @ApiModelProperty("联系方式：电话")
    private String mobile;

    @NotNull
    @ApiModelProperty("联系方式：固话")
    private String telephone;

    @NotNull
    @ApiModelProperty("地址")
    private String address;

    @NotNull
    @ApiModelProperty("所属区域编码")
    private String districtCode;

    @ApiModelProperty("厂家类型，例如服装类、办公消耗类等")
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, fetch = EAGER)
    private Set<PayoutSubType> type = new HashSet<>();

    private Instant createdAt;

    public Supplier(){

    }

    public Supplier(String name, String contacts, String mobile, String telephone, String address, String districtCode, Instant createdAt) {
        this.name = name;
        this.contacts = contacts;
        this.mobile = mobile;
        this.telephone = telephone;
        this.address = address;
        this.districtCode = districtCode;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Set<PayoutSubType> getType() {
        return type;
    }

    public void setType(Set<PayoutSubType> type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", mobile='" + mobile + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}
