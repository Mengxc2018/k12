package cn.k12soft.servo.module.wxLogin.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

@ApiModel
public class TemplateMessage {

    @ApiModelProperty("过期时间")
    private String timeoutAt;   // 过期/超时时间

    @ApiModelProperty(value = "所需下发的模板消息的id")
    private String templateId; // TEMPLATE_ID

    @ApiModelProperty(value = "点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。")
    private String page;    // index

    @ApiModelProperty(value = "表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id")
    private String formId;  // FORMID   模版id

    @ApiModelProperty(value = "模板需要放大的关键词，不填则默认无放大")
    private String emphasisKeyword; // keyword1.DATA

    @ApiModelProperty(value = "模板内容，不填则下发空模板")
    private JSONObject data;

    public TemplateMessage(){}

    public TemplateMessage(String timeoutAt, String templateId, String page, String formId, JSONObject data, String emphasisKeyword) {
        this.timeoutAt = timeoutAt;
        this.templateId = templateId;
        this.page = page;
        this.formId = formId;
        this.data = data;
        this.emphasisKeyword = emphasisKeyword;

    }

    public String getTemplateId() {
        return templateId;
    }

    public String getPage() {
        return page;
    }

    public String getFormId() {
        return formId;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public String getTimeoutAt() {
        return timeoutAt;
    }

    public JSONObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TemplateMessage{" +
                ", templateId='" + templateId + '\'' +
                ", page='" + page + '\'' +
                ", formId='" + formId + '\'' +
                ", emphasisKeyword='" + emphasisKeyword + '\'' +
                ", data=" + data +
                '}';
    }
}
