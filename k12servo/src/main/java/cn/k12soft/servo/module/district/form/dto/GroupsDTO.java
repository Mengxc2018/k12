package cn.k12soft.servo.module.district.form.dto;


import cn.k12soft.servo.module.zone.domain.Regions;

import java.util.Collection;

public class GroupsDTO {
    private Integer id;
    private String label;
    private String code;
    private Collection<RegionsDTO> children;

    public GroupsDTO(Integer id, String label, String code) {
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

    public Collection<RegionsDTO> getChildren() {
        return children;
    }

    public void setChildren(Collection<RegionsDTO> children) {
        this.children = children;
    }
}
