package cn.k12soft.servo.module.activiti.processNode.domain.dto;

public class MassageCC {
    private String actorId;
    private String name;
    private String portrait;

    public MassageCC(String actorId, String name, String portrait) {
        this.actorId = actorId;
        this.name = name;
        this.portrait = portrait;
    }

    public String getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public String getPortrait() {
        return portrait;
    }
}
