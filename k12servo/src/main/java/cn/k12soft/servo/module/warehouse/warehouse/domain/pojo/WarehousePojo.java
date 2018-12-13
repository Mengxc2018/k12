package cn.k12soft.servo.module.warehouse.warehouse.domain.pojo;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 仓库物品汇总
 */
@MappedSuperclass
public class WarehousePojo {

    @NotNull
    private String name;    // 商品名称
    @NotNull
    private String isbn;    // 商品isbn码、条形码
    private String spec;    // 商品规格
    private Instant createdAt;  // 创建时间

    public WarehousePojo() {
    }

    public WarehousePojo(String name, String isbn, String spec, Instant createdAt) {
        this.name = name;
        this.isbn = isbn;
        this.spec = spec;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
