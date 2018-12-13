package cn.k12soft.servo.domain.enumeration;

/**
 * 身体检查
 */
public class Physical {

    public enum TYPE{
        MORNING,    // 早
        NOON,       // 中
        NIGHT,      // 晚
    }

    // 精神
    public enum SPIRIT{
        WELL,       // 良好
        DOLDRUMS,   // 精神不振
        SAG,        // 萎靡
        TOOEXCITED, // 过于兴奋
        TOOAGITATED // 过于烦躁
    }

    // 身体
    public enum BODY{
        WELL,       // 正常
        COUGH,      // 咳嗽
        RHINORRHEA, // 流鼻涕
        FEVERHIGH,  // 高烧
        EVERLOWF,   // 低烧
        DIARRHEA    // 腹泻
    }

    // 皮肤
    public enum  SINK{
        WELL,           // 正常
        RASH,           // 皮疹
        ALLERGY,        // 过敏
        BODYHURT,       // 身体外伤
        FACEHURT,       // 脸部外伤
        MOSQUITOHURT    // 蚊虫叮咬
    }

    // 正餐
    public enum DINNER{
        WELL,   // 正常
        LESSEATTING,    // 饭量少:饭吃的较少
        LESSVEGETABLE,      // 蔬菜少：蔬菜吃的少
        LESSMEAT,       // 肉类少：肉类吃的少
        TEACHERHELP     // 老师喂：需要老师喂
    }

    // 午睡
    public enum AFTERNAP{
        WELL,       // 正常
        LESS,       // 少于30分钟
        NOSELEEP    // 没睡
    }

    // 加餐
    public enum  ADDFOOD{
        WELL,       // 正常
        LESSDRINK,  //  饮品少:饮品喝的少
        LESSFRUIT,  // 水果少:水果吃的少
        LESSMEAT,   // 肉类少：肉类吃的少
        MEDICINE    // 药已吃
    }

    // 大小便，排泄
    public enum EXCRETE{
        WELL,    // 正常
        YELLO,   // 小便黄
        LESS,    // 小便少
        CONSTIPATION,   // 便秘
        DIARRHEA    // 拉稀
    }

    // 口腔
    public enum MOUTH{
        WELL,   // 正常
        HERPAS, // 疱疹
        ULCERATION, // 溃疡
        THROATRED   // 咽部发红
    }

    // 其他
    public enum OTHER{
        MEDICINE,       // 携带药物
        DANGEROUS,      // 携带危险物品
        NOTHING         // 无
    }
}
