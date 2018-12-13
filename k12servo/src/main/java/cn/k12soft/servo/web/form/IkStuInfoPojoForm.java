package cn.k12soft.servo.web.form;

import java.util.LinkedList;
import java.util.List;

public class IkStuInfoPojoForm {
    private String iklassName;
    private List<InnerPojoForm> stuInfos;

    public String getIklassName() {
        return iklassName;
    }

    public List<InnerPojoForm> getStuInfos() {
        return stuInfos;
    }

    public List<String[]> getStuInfoList(){
        List<String[]> list = new LinkedList<>();
        for (InnerPojoForm form : stuInfos) {
            list.add(new String[]{form.getStuId()+"", form.getStuName(), form.getNote()});
        }
        return list;
    }
}

class InnerPojoForm{
    private Integer stuId;
    private String stuName;
    private String note;// 备注

    public Integer getStuId() {
        return stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public String getNote() {
        return note;
    }
}