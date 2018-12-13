package cn.k12soft.servo.domain.enumeration;

public enum KlassType {
    COMMON(0, "普通班"),
    INTEREST_SMALL(1, "兴趣班(小)"),//兴趣班 小班 一对一教学
    INTEREST_BIG(2, "兴趣班(大)"),//兴趣班 大班 一对多教学
    ;

    KlassType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    private int id;
    private String name;

    public static KlassType get(Integer type) {
        KlassType[] typs = values();
        for (KlassType ge : typs) {
            if (ge.getId() == type) {
                return ge;
            }
        }
        return null;
    }
}
