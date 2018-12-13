package cn.k12soft.servo.web.form;

import cn.k12soft.servo.domain.enumeration.UserState;

public class UserPojo {
    private long id;
    private String username;
    private String mobile;
    private UserState userState;
    private String stuName;
    private String klassName;

     public UserPojo(Object[] clo) {
        this.id = Long.parseLong(clo[0].toString());
        this.username = (String) clo[1];
        this.mobile = (String) clo[2];
        this.userState = (UserState.valueOf(clo[3].toString()));
        this.stuName = (String) clo[4];
        this.klassName = (String) clo[5];
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMobile() {
        return mobile;
    }

    public UserState getUserState() {
        return userState;
    }

    public String getStuName() {
        return stuName;
    }

    public String getKlassName() {
        return klassName;
    }
}
