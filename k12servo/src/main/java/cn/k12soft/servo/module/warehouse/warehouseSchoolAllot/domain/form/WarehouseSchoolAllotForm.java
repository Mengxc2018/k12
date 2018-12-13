package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.form;

import cn.k12soft.servo.domain.enumeration.AllotType;
import cn.k12soft.servo.module.warehouse.warehouse.domain.form.WarehouseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class WarehouseSchoolAllotForm extends WarehouseForm{

    @ApiModelProperty("分配数量")
    private Integer allotNum;    // 分配数量

    @ApiModelProperty("分配类型，给班级还是给部门，可在下方applyBy注明详细信息")
    private AllotType type;    // 分配类型

    @ApiModelProperty("申请单位/人")
    private String applyBy;     // 申请单位/人

    @ApiModelProperty("单价")
    private Float price;        // 单价

    public Integer getAllotNum() {
        return allotNum;
    }

    public String getApplyBy() {
        return applyBy;
    }

    public AllotType getType() {
        return type;
    }

    public Float getPrice() {
        return price;
    }
}
