package cn.k12soft.servo.module.warehouse.warehouseKlassLog.domain;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WarehouseOperationtype;
import cn.k12soft.servo.module.warehouse.warehouse.domain.pojo.WarehouseLogPojo;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@DynamicUpdate
public class WarehouseKlassLog extends WarehouseLogPojo{

    @Id
    @GeneratedValue
    private Long id;
    private Integer schoolId;
    @OneToOne
    private Klass klass;

    public WarehouseKlassLog() {
    }

    public WarehouseKlassLog(Integer schoolId, Klass klass) {
        this.schoolId = schoolId;
        this.klass = klass;
    }

    public WarehouseKlassLog(String name, String isbn, String spec, Float price, Integer oldNum, Integer newNum, Integer num, WarehouseOperationtype operationtype, User createdBy, Instant createdAt, Integer schoolId, Klass klass) {
        super(name, isbn, spec, price, oldNum, newNum, num, operationtype, createdBy, createdAt);
        this.schoolId = schoolId;
        this.klass = klass;
    }

    public Long getId() {
        return id;
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

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }
}
