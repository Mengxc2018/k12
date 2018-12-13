package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.ActorType;

/**
 * Created by liubing on 2018/7/24
 */
public class RegionUserDTO {
    private Integer id;
    private ActorType actorType;
//    private Actor actor;

    private RegionUserDTO(Actor actor, ActorType actorType){
        this.id = actor.getId();
        this.actorType = actorType;
    }

    public Integer getId() {
        return id;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public static RegionUserDTO create(Actor actor, ActorType actorType){
        return new RegionUserDTO(actor, actorType);
    }
}
