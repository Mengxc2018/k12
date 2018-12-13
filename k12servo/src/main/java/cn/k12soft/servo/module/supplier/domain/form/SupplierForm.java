package cn.k12soft.servo.module.supplier.domain.form;

import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

/**
 * 供应商信息
 */
@ApiModel
public class SupplierForm {

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
    @ManyToOne
    private Set<Integer> type;

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

    public Set<Integer> getType() {
        return type;
    }
}
