package cn.k12soft.servo.module.warehouse.warehouse.domain.pojo;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WarehouseOperationtype;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.time.Instant;

@MappedSuperclass
public class WarehouseLogPojo {

    private String name;    // 商品名称
    private String isbn;    // 商品isbn码、条形码
    private String spec;    // 商品规格
    private Float price;    // 单价
    private Integer oldNum;     // 剩余多少
    private Integer newNum;     // 更新后多少
    private Integer num;        // 更新数量
    @Enumerated(EnumType.STRING)
    private WarehouseOperationtype operationtype;   // 操作方式
    @OneToOne
    private User createdBy;     // 操作人
    private Instant createdAt;  // 更新时间

    public WarehouseLogPojo(){}

    public WarehouseLogPojo(String name, String isbn, String spec, Float price, Integer oldNum, Integer newNum, Integer num, WarehouseOperationtype operationtype, User createdBy, Instant createdAt) {
        this.name = name;
        this.isbn = isbn;
        this.spec = spec;
        this.price = price;
        this.oldNum = oldNum;
        this.newNum = newNum;
        this.num = num;
        this.operationtype = operationtype;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
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

    public Integer getOldNum() {
        return oldNum;
    }

    public void setOldNum(Integer oldNum) {
        this.oldNum = oldNum;
    }

    public Integer getNewNum() {
        return newNum;
    }

    public void setNewNum(Integer newNum) {
        this.newNum = newNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public WarehouseOperationtype getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(WarehouseOperationtype operationtype) {
        this.operationtype = operationtype;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
