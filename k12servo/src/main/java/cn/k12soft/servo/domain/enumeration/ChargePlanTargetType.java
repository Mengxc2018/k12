package cn.k12soft.servo.domain.enumeration;

public enum ChargePlanTargetType {
    COMMON_KLASS(0, "普通班"),
    INTEREST_KLASS(1, "兴趣班"),
    STUDENT(3,"指定儿童"),
    ;
    ChargePlanTargetType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    private int id;
    private String name;

    public static ChargePlanTargetType get(Integer type) {
        ChargePlanTargetType[] typs = values();
        for (ChargePlanTargetType ge : typs) {
            if (ge.getId() == type) {
                return ge;
            }
        }
        return null;
    }
}
