package cn.k12soft.servo.module.district.form.dto;


import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.service.dto.SchoolPojoDTO;

import java.util.Collection;
import java.util.List;

public class ProvincesDTO {
    private Integer id;
    private String label;
    private String code;
    private Collection<CitysDTO> children;

    public ProvincesDTO(Integer id, String label, String code) {
        this.id = id;
        this.label = label;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<CitysDTO> getChildren() {
        return children;
    }

    public void setChildren(Collection<CitysDTO> children) {
        this.children = children;
    }
}
