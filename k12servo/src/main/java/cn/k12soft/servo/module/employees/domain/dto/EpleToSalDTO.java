package cn.k12soft.servo.module.employees.domain.dto;

import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;

import javax.persistence.Column;

/**
 * 员工组装工资表
 */
public class EpleToSalDTO {

    private Long id;
    private String name;
    private TeacherSocialSecurity teacherSocialSecurity;

    public EpleToSalDTO(){}

    public EpleToSalDTO(Long id, String name, TeacherSocialSecurity teacherSocialSecurity) {
        this.id = id;
        this.name = name;
        this.teacherSocialSecurity = teacherSocialSecurity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TeacherSocialSecurity getTeacherSocialSecurity() {
        return teacherSocialSecurity;
    }

    public EpleToSalDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public EpleToSalDTO setName(String name) {
        this.name = name;
        return this;
    }

    public EpleToSalDTO setTeacherSocialSecurity(TeacherSocialSecurity teacherSocialSecurity) {
        this.teacherSocialSecurity = teacherSocialSecurity;
        return this;
    }
}
