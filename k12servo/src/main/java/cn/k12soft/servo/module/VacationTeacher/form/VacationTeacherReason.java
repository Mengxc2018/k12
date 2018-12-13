package cn.k12soft.servo.module.VacationTeacher.form;

public class VacationTeacherReason {

    public enum RETRO{
        SICK,       // 病假
        ANNUAL,     // 年休假
        OTHER      // 其他假
    }

    public enum VACATION {
        SICK,       // 病假
        ANNUAL,     // 年休假
        OTHER,      // 其他假
        REST,       // 调休
        AFFAIR,     // 事假
        INDUCTRIAL, // 工伤假
        LACTION,    // 哺乳假
        MARRUAGE,   // 婚假
        FUNERAL    // 丧假
    }
}
