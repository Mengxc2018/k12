package cn.k12soft.servo.module.activiti.processNode.domain.dto;

/**
 * 查询时返回，存放actorId及职位
 */
public class NodeQueryListDTO {

    private Integer actorId;    // 员工actorId
    private String actorName;   // 员工姓名
    private String portrait;    // 员工头像
    private Integer dutyId;     // 职务id
    private String dutyName;    // 职务名

    public NodeQueryListDTO(Integer actorId, String actorName, String portrait, Integer dutyId, String dutyName) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.portrait = portrait;
        this.dutyId = dutyId;
        this.dutyName = dutyName;
    }

    public Integer getActorId() {
        return actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public String getDutyName() {
        return dutyName;
    }

    public String getPortrait() {
        return portrait;
    }
}
