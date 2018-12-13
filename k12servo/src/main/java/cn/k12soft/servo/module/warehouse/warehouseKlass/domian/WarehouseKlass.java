package cn.k12soft.servo.module.warehouse.warehouseKlass.domian;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.warehouse.warehouse.domain.pojo.WarehousePojo;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@DynamicUpdate
public class WarehouseKlass extends WarehousePojo{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer num;     // 库存总数量

    @NotNull
    @Enumerated(EnumType.STRING)
    private WarehouseSubclass subclass; // 小类，子类

    @NotNull
    @Enumerated(EnumType.STRING)
    private WareHouseSuper superclass; // 大类，父类

    @OneToOne
    private Klass klass;

    private Float price;    // 单价

    @NotNull
    private Integer schoolId;

    private Instant updateAt;   // 上次购买或更新时间
    private Instant createdAt;   // 创建时间

    public WarehouseKlass() {
    }

    public WarehouseKlass(String name, String isbn, String spec, Instant createdAt) {
        super(name, isbn, spec, createdAt);
    }

    public WarehouseKlass(Integer num, WarehouseSubclass subclass, WareHouseSuper superclass, Klass klass, Integer schoolId, Instant updateAt, Instant createdAt) {
        this.num = num;
        this.subclass = subclass;
        this.superclass = superclass;
        this.klass = klass;
        this.schoolId = schoolId;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
    }

    public WarehouseKlass(Integer num, WarehouseSubclass subclass, WareHouseSuper superclass, Klass klass, Float price, Integer schoolId, Instant updateAt, Instant createdAt) {
        this.num = num;
        this.subclass = subclass;
        this.superclass = superclass;
        this.klass = klass;
        this.price = price;
        this.schoolId = schoolId;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
    }

    public WarehouseKlass(String name, String isbn, String spec, Instant createdAt, Integer num, WarehouseSubclass subclass, WareHouseSuper superclass, Klass klass, Float price, Integer schoolId, Instant updateAt, Instant createdAt1) {
        super(name, isbn, spec, createdAt);
        this.num = num;
        this.subclass = subclass;
        this.superclass = superclass;
        this.klass = klass;
        this.price = price;
        this.schoolId = schoolId;
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

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
