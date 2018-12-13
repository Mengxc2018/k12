package cn.k12soft.servo.domain.enumeration;

public enum StudentAccountOpType {
    PRE_PAY(0, "预缴费"),
    REFUND(1, "费种转入"),
    ENROLMENT(2, "报到"),
    UPDATE_PRE_PAY(3, "接口修改"),
    ;

    StudentAccountOpType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    private int id;
    private String name;

    public static StudentAccountOpType get(Integer type) {
        StudentAccountOpType[] typs = values();
        for (StudentAccountOpType ge : typs) {
            if (ge.getId() == type) {
                return ge;
            }
        }
        return null;
    }
}
