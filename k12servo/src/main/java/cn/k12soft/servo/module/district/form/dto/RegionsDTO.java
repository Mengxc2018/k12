package cn.k12soft.servo.module.district.form.dto;


import cn.k12soft.servo.module.zone.domain.Provinces;

import java.util.Collection;

public class RegionsDTO {
    private Integer id;
    private String label;
    private String code;
    private Collection<ProvincesDTO> children;

    public RegionsDTO(Integer id, String label, String code) {
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

    public Collection<ProvincesDTO> getChildren() {
        return children;
    }

    public String getCode() {
        return code;
    }

    public Collection<ProvincesDTO> getProvinces() {
        return children;
    }

    public void setProvinces(Collection<ProvincesDTO> children) {
        this.children = children;
    }
}
