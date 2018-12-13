package cn.k12soft.servo.module.applyFor.domain.dto;

public class ApplyForNowDTO {
    private Integer masterId;           // 审批人id
    private String masterName;          // 审批人姓名
    private String masterPortrait;      // 审批人头像

    public ApplyForNowDTO() {

    }

    public ApplyForNowDTO(Integer masterId, String masterName, String masterPortrait) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterPortrait = masterPortrait;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getMasterPortrait() {
        return masterPortrait;
    }
}
