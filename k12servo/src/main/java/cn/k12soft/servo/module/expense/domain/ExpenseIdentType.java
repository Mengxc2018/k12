package cn.k12soft.servo.module.expense.domain;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
public enum ExpenseIdentType {
  USER("普通"),
  MEMBER("员工"),
  PROPRIETOR("业主"),
  OTHER("其他");

  private final String zh_CN;

  ExpenseIdentType(String zh_CN) {
    this.zh_CN = zh_CN;
  }

  public String getZh_CN() {
    return zh_CN;
  }
}
