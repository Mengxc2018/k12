package cn.k12soft.servo.module.warehouse.warehouseSchool.domain;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.warehouse.warehouse.domain.pojo.WarehousePojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 学校库存
 */
@Entity
@DynamicUpdate
@ApiModel
public class WarehouseSchool extends WarehousePojo {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty("单价")
    private Float price;    // 单价

    @ApiModelProperty("库存总数量")
    @NotNull
    private Integer num;     // 库存总数量

    @ApiModelProperty("单位")
    private String standar;     // 单位数量

    @ApiModelProperty("小类，子类")
    @NotNull
    @Enumerated(EnumType.STRING)
    private WarehouseSubclass subclass; // 小类，子类

    @ApiModelProperty("大类，父类")
    @NotNull
    @Enumerated(EnumType.STRING)
    private WareHouseSuper superclass; // 大类，父类

    @NotNull
    private Integer schoolId;

    @OneToOne
    private User createdBy;     // 创建人
    private Instant updateAt;   // 上次购买或更新时间
    private Instant createdAt;   // 创建时间

    public WarehouseSchool() {
    }

    public WarehouseSchool(Float price, Integer num, String standar, WarehouseSubclass subclass, WareHouseSuper superclass, Integer schoolId, User createdBy, Instant updateAt, Instant createdAt) {
        this.price = price;
        this.num = num;
        this.standar = standar;
        this.subclass = subclass;
        this.superclass = superclass;
        this.schoolId = schoolId;
        this.createdBy = createdBy;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
    }

    public WarehouseSchool(String name, String isbn, String spec, Instant createdAt, Float price, Integer num, String standar, WarehouseSubclass subclass, WareHouseSuper superclass, Integer schoolId, User createdBy, Instant updateAt, Instant createdAt1) {
        super(name, isbn, spec, createdAt);
        this.price = price;
        this.num = num;
        this.standar = standar;
        this.subclass = subclass;
        this.superclass = superclass;
        this.schoolId = schoolId;
        this.createdBy = createdBy;
        this.updateAt = updateAt;
        this.createdAt = createdAt1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStandar() {
        return standar;
    }

    public void setStandar(String standar) {
        this.standar = standar;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public WarehouseSubclass getSubclass() {
        return subclass;
    }

    public void setSubclass(WarehouseSubclass subclass) {
        this.subclass = subclass;
    }

    public WareHouseSuper getSuperclass() {
        return superclass;
    }

    public void setSuperclass(WareHouseSuper superclass) {
        this.superclass = superclass;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
