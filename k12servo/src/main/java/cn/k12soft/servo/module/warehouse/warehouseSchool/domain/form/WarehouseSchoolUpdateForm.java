package cn.k12soft.servo.module.warehouse.warehouseSchool.domain.form;

import cn.k12soft.servo.module.warehouse.warehouse.domain.form.WarehouseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@ApiModel
public class WarehouseSchoolUpdateForm extends WarehouseForm{

    @ApiModelProperty(value = "要添加的数量")
    @NotNull
    private Integer num;    // 数量

    @ApiModelProperty("购买时间")
    private Instant buyAt;

    public Instant getBuyAt() {
        return buyAt;
    }

    public Integer getNum() {
        return num;
    }
}
