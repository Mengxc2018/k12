package cn.k12soft.servo.module.warehouse.warehouseSchool.domain.form;

import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.warehouse.warehouse.domain.form.WarehouseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class WarehouseSchoolForm extends WarehouseForm{

    @ApiModelProperty(value = "当前库存数量")
    private Integer num;     // 当前库存数量
    @ApiModelProperty("单位数量")
    private String standar;     // 单位数量
    @ApiModelProperty(value = "小类，子类")
    private WarehouseSubclass subclass; // 小类，子类
    @ApiModelProperty(value = "大类，父类")
    private WareHouseSuper superclass; // 大类，父类

    public WarehouseSchoolForm() {
    }

    public Integer getNum() {
        return num;
    }

    public String getStandar() {
        return standar;
    }

    public WarehouseSubclass getSubclass() {
        return subclass;
    }

    public WareHouseSuper getSuperclass() {
        return superclass;
    }
}
