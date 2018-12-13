package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.dto;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.AllotType;

import java.time.Instant;

public class WarehouseSchoolAllotDTO {
    private Long id;
    private String name;    // 商品名称
    private String isbn;    // 商品isbn码、条形码
    private String spec;    // 商品规格
    private Integer wareNum; // 库存数量
    private Integer allotNum;    // 分配数量
    private AllotType type;     // 分配类型，分配到部门还是班级
    private Object applyBy;     // 申请单位/人
    private Float price;        // 单价
    private boolean isAllot;    // 是否领取
    private User createdBy;          // 操作者
    private User allotBy;           // 领取人
    private Instant createdAt;
    private Integer schoolId;

    public WarehouseSchoolAllotDTO() {
    }

    public WarehouseSchoolAllotDTO(Long id, String name, String isbn, String spec, Integer wareNum, Integer allotNum, AllotType type, Object applyBy, Float price, boolean isAllot, User createdBy, User allotBy, Instant createdAt, Integer schoolId) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.spec = spec;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSpec() {
        return spec;
    }

    public Integer getWareNum() {
        return wareNum;
    }

    public Integer getAllotNum() {
        return allotNum;
    }

    public AllotType getType() {
        return type;
    }

    public Object getApplyBy() {
        return applyBy;
    }

    public Float getPrice() {
        return price;
    }

    public boolean getIsAllot() {
        return isAllot;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public User getAllotBy() {
        return allotBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Integer getSchoolId() {
        return schoolId;
    }
}
