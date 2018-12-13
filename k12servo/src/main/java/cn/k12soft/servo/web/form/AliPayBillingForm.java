package cn.k12soft.servo.web.form;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by liubing on 2018/7/4
 */
public class AliPayBillingForm {
    @NotNull
    private List<Map<String, String>> userDetailMap;
    @NotNull
    private int studentId;
    private String outTradeNo;//SV端的缴费账单编号
    private String chargeBillTitle; //缴费账单名称
    private long gmtEnd;
    private String endEnable;

    public List<Map<String, String>> getUserDetailMap() {
        return userDetailMap;
    }

    public void setUserDetailMap(List<Map<String, String>> userDetailMap) {
        this.userDetailMap = userDetailMap;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getChargeBillTitle() {
        return chargeBillTitle;
    }

    public void setChargeBillTitle(String chargeBillTitle) {
        this.chargeBillTitle = chargeBillTitle;
    }

    public long getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(long gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public String getEndEnable() {
        return endEnable;
    }

    public void setEndEnable(String endEnable) {
        this.endEnable = endEnable;
    }

    public String getUserDetails() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(this.userDetailMap);
        return jsonArray.toString();
    }
}
