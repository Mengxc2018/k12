package cn.k12soft.servo.module.errcode.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicUpdate
public class ErrCode {

    @Id
    @GeneratedValue
    private Long id;
    private String errcode;
    private String errmsg;

    public ErrCode() {
    }

    public ErrCode(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
