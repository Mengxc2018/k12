package cn.k12soft.servo.module.warehouse.warehouseKlass.domian.form;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.warehouse.warehouse.domain.form.WarehouseForm;

public class WarehouseKlassForm extends WarehouseForm {

    private Integer num;     // 库存总数量
    private WarehouseSubclass subclass; // 小类，子类
    private WareHouseSuper superclass; // 大类，父类
    private Integer klass;

    public Integer getNum() {
        return num;
    }

    public WarehouseSubclass getSubclass() {
        return subclass;
    }

    public WareHouseSuper getSuperclass() {
        return superclass;
    }

    public Integer getKlass() {
        return klass;
    }
}
