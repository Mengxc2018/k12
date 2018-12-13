package cn.k12soft.servo.module.medicine.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

@ApiModel
public class MedicineFindByDTO {
    private Long id;
    private String stuName;
    @ApiModelProperty("学生头像")
    private String avatar;
    private Instant date;

    public MedicineFindByDTO() {
    }

    public MedicineFindByDTO(Long id, String stuName, String avatar, Instant date) {
        this.id = id;
        this.stuName = stuName;
        this.avatar = avatar;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getStuName() {
        return stuName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Instant getDate() {
        return date;
    }
}
