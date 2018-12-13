package cn.k12soft.servo.module.expense.domain;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
public enum ExpensePeriodType {
  YEAR("年"),
  HALF_YEAR("学期"),
  QUARTER("季度"),
  MONTH("月"),
  WEEK("周"),
  DAY("日"),
  ONCE("一次性");

  private String zh_CN;

  ExpensePeriodType(String zh_CN) {
    this.zh_CN = zh_CN;
  }

  public String getZh_CN() {
    return zh_CN;
  }
}
