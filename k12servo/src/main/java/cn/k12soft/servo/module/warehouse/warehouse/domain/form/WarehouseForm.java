package cn.k12soft.servo.module.warehouse.warehouse.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class WarehouseForm {
    @ApiModelProperty(value = " 商品名称")
    private String name;    // 商品名称
    @ApiModelProperty(value = " 商品isbn码，条形码")
    private String isbn;    // 商品isbn码、条形码
    @ApiModelProperty(value = " 商品规格")
    private String spec;    // 商品规格
    @ApiModelProperty(value = " 商品单价")
    private Float price;    // 商品单价

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSpec() {
        return spec;
    }

    public Float getPrice() {
        return price;
    }
}
