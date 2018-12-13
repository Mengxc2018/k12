package cn.k12soft.servo.domain.enumeration;

public enum FeeKindType {

    VIDEOS(0, "视频");  // 视频

    private Integer id;
    private String name;

    FeeKindType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FeeKindType get(Integer type) {
        FeeKindType[] typs = values();
        for (FeeKindType ge : typs) {
            if (ge.getId() == type) {
                return ge;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
