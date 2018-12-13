package cn.k12soft.servo.module.warehouse.warehouseSchoolLog.domain;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WarehouseOperationtype;
import cn.k12soft.servo.module.warehouse.warehouse.domain.pojo.WarehouseLogPojo;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 学校物品更新记录
 */
@Entity
@DynamicUpdate
public class WarehouseSchoolLog extends WarehouseLogPojo{

    @Id
    @GeneratedValue
    private Long id;
    private Integer schoolId;
    @OneToOne
    private Klass klass;      // 保存班级或者其他

    private Instant buyAt;  // 购买时间

    public WarehouseSchoolLog() {
    }

    public WarehouseSchoolLog(Integer schoolId, Klass klass, Instant buyAt) {
        this.schoolId = schoolId;
        this.klass = klass;
        this.buyAt = buyAt;
    }

    public WarehouseSchoolLog(String name, String isbn, String spec, Float price, Integer oldNum, Integer newNum, Integer num, WarehouseOperationtype operationtype, User createdBy, Instant createdAt, Integer schoolId, Klass klass, Instant buyAt) {
        super(name, isbn, spec, price, oldNum, newNum, num, operationtype, createdBy, createdAt);
        this.schoolId = schoolId;
        this.klass = klass;
        this.buyAt = buyAt;
    }

    public Long getId() {
        return id;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getBuyAt() {
        return buyAt;
    }
}
