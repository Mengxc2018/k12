package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.AllotType;
import cn.k12soft.servo.module.warehouse.warehouse.domain.pojo.WarehousePojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;

/**
 * 学校分配物资表
 */
@Entity
@DynamicUpdate
@ApiModel
public class WarehouseSchoolAllot extends WarehousePojo{

    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty("库存数量")
    private Integer wareNum; // 库存数量

    @ApiModelProperty("分配数量")
    private Integer allotNum;    // 分配数量

    @ApiModelProperty("分配类型，分配到部门还是班级")
    private AllotType type;     // 分配类型，分配到部门还是班级

    @ApiModelProperty("申请单位/人")
    private String applyBy;     // 申请单位/人

    @ApiModelProperty("单价")
    private Float price;        // 单价

    @ApiModelProperty("是否领取")
    private boolean isAllot;    // 是否领取

    @OneToOne
    @ApiModelProperty("操作者")

    private User createdBy;          // 操作者
    @OneToOne
    @ApiModelProperty("领取人")
    private User allotBy;           // 领取人

    @ApiModelProperty("创建时间")
    private Instant createdAt;
    private Integer schoolId;

    public WarehouseSchoolAllot() {
    }

    public WarehouseSchoolAllot(String name, String isbn, String spec, Instant createdAt) {
        super(name, isbn, spec, createdAt);
    }

    public WarehouseSchoolAllot(Integer wareNum, Integer allotNum, AllotType type, String applyBy, Float price, boolean isAllot, User createdBy, User allotBy, Instant createdAt, Integer schoolId) {
        this.wareNum = wareNum;
        this.allotNum = allotNum;
        this.type = type;
        this.applyBy = applyBy;
        this.price = price;
        this.isAllot = isAllot;
        this.createdBy = createdBy;
        this.allotBy = allotBy;
        this.createdAt = createdAt;
        this.schoolId = schoolId;
    }

    public WarehouseSchoolAllot(String name, String isbn, String spec, Instant createdAt, Integer wareNum, Integer allotNum, AllotType type, String applyBy, Float price, boolean isAllot, User createdBy, User allotBy, Instant createdAt1, Integer schoolId) {
        super(name, isbn, spec, createdAt);
        this.wareNum = wareNum;
        this.allotNum = allotNum;
        this.type = type;
        this.applyBy = applyBy;
        this.price = price;
        this.isAllot = isAllot;
        this.createdBy = createdBy;
        this.allotBy = allotBy;
        this.createdAt = createdAt1;
        this.schoolId = schoolId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWareNum() {
        return wareNum;
    }

    public void setWareNum(Integer wareNum) {
        this.wareNum = wareNum;
    }

    public Integer getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(Integer allotNum) {
        this.allotNum = allotNum;
    }

    public String getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean getIsAllot() {
        return isAllot;
    }

    public void setAllot(boolean allot) {
        isAllot = allot;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

    public User getAllotBy() {
        return allotBy;
    }

    public void setAllotBy(User allotBy) {
        this.allotBy = allotBy;
    }

    public AllotType getType() {
        return type;
    }

    public void setType(AllotType type) {
        this.type = type;
    }
}
