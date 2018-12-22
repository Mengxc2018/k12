package cn.k12soft.servo.module.district.form.dto;

import cn.k12soft.servo.service.dto.SchoolPojoDTO;

import java.util.Collection;

public class CitysDTO {
    private Integer id;
    private String label;
    private String code;
    private Collection<SchoolPojoDTO> children;

    public CitysDTO(Integer id, String label, String code) {
        this.id = id;
        this.label = label;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getCode() {
        return code;
    }

    public Collection<SchoolPojoDTO> getChildren() {
        return children;
    }

    public void setChildren(Collection<SchoolPojoDTO> children) {
        this.children = children;
    }
}
