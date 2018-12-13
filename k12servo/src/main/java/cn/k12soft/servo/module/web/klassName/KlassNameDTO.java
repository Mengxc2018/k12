package cn.k12soft.servo.module.web.klassName;

public class KlassNameDTO {

    private Integer klassid;
    private String klassName;

    public KlassNameDTO() {
    }

    public KlassNameDTO(Integer klassid, String klassName) {
        this.klassid = klassid;
        this.klassName = klassName;
    }

    public Integer getKlassid() {
        return klassid;
    }

    public String getKlassName() {
        return klassName;
    }
}
