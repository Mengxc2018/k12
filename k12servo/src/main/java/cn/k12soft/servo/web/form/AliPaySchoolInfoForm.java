package cn.k12soft.servo.web.form;

/**
 * Created by liubing on 2018/7/2
 */
public class AliPaySchoolInfoForm {
    private int schollId;
//    private String provinceCode; //省份的国家编码（国家统计局出版的行政区划代码 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/）
    private String provinceName; //省名称
//    private String cityCode; //城市的国家编码（国家统计局出版的行政区划代码 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/）
    private String cityName;//	城市名称
//    private String districtCode;    //区县的国家编码（国家统计局出版的行政区划代码 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/）
    private String districtName;//	String	必选	64	区县名称
    private String isvName;//	String	必选	256	商家名称，每个接入教育缴费的ISV商家名称，由ISV自己提供
    private String isvNotifyUrl;//	String	必选	256	此通知地址是为了保持教育缴费平台与ISV商户支付状态一致性。用户支付成功后，支付宝会根据本isv_notify_url，通过POST请求的形式将支付结果作为参数通知到商户系统，ISV商户可以根据返回的参数更新账单状态。
    private String isvPid;//	String	必选	128	填写已经签约教育缴费的isv的支付宝PID
    private String isvPhone;//	String	必选	20	ISV联系电话,用于账单详情页面显示
    private String schoolPid;//	String	必选	128	学校用来签约支付宝教育缴费的支付宝PID

    public int getSchollId() {
        return schollId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getIsvName() {
        return isvName;
    }

    public void setIsvName(String isvName) {
        this.isvName = isvName;
    }

    public String getIsvNotifyUrl() {
        return isvNotifyUrl;
    }

    public void setIsvNotifyUrl(String isvNotifyUrl) {
        this.isvNotifyUrl = isvNotifyUrl;
    }

    public String getIsvPid() {
        return isvPid;
    }

    public void setIsvPid(String isvPid) {
        this.isvPid = isvPid;
    }

    public String getIsvPhone() {
        return isvPhone;
    }

    public void setIsvPhone(String isvPhone) {
        this.isvPhone = isvPhone;
    }

    public String getSchoolPid() {
        return schoolPid;
    }

    public void setSchoolPid(String schoolPid) {
        this.schoolPid = schoolPid;
    }

}
