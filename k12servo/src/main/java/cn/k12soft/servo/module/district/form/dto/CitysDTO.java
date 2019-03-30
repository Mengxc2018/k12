package cn.k12soft.servo.module.district.form.dto;

import cn.k12soft.servo.module.department.domain.Dept;
import cn.k12soft.servo.service.dto.SchoolPojoDTO;

import java.util.Collection;
import java.util.Set;

public class CitysDTO {
    private Integer id;
    private String label;
    private String code;
    private Set<Dept> depts;
    private Collection<SchoolPojoDTO> children;

    public CitysDTO(Integer id, String label, String code, Set<Dept> depts, Collection<SchoolPojoDTO> children) {
        this.id = id;
        this.label = label;
        this.code = code;
        this.depts = depts;
        this.children = children;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDepts(Set<Dept> depts) {
        this.depts = depts;
    }
}
