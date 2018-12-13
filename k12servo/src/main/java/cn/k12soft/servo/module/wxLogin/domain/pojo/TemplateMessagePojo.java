package cn.k12soft.servo.module.wxLogin.domain.pojo;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.wxLogin.domain.form.TemplateMessage;
import net.sf.json.JSONObject;

public class TemplateMessagePojo {

    private TemplateMessage templateMessage;    // 要发送的数据

    private JSONObject jsonObject;              // 要保存的数据

    private Actor actors;                       // 要发送的人的actor

    public TemplateMessagePojo(){}

    public TemplateMessagePojo(TemplateMessage templateMessage, JSONObject jsonObject, Actor actors) {
        this.templateMessage = templateMessage;
        this.jsonObject = jsonObject;
        this.actors = actors;
    }

    public TemplateMessage getTemplateMessage() {
        return templateMessage;
    }

    public void setTemplateMessage(TemplateMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public Actor getActors() {
        return actors;
    }

    public void setActors(Actor actors) {
        this.actors = actors;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String toString() {
        return "TemplateMessagePojo{" +
                "templateMessage=" + templateMessage +
                ", jsonObject=" + jsonObject +
                ", actors=" + actors +
                '}';
    }
}
