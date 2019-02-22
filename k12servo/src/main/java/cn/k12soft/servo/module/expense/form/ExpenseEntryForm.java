package cn.k12soft.servo.module.expense.form;

import cn.k12soft.servo.module.expense.domain.ExpenseIdentType;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodType;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
public class ExpenseEntryForm {

  private String name;                                    // 收费名称
  private ExpensePeriodType periodType;                   // 周期类型
  private BigDecimal amount;                              // 原始金额
  private List<PeriodDiscount> periodDiscounts;           // 周期性折扣
  private List<IdentDiscount> identDiscounts;             // 身份类型折扣
  private List<InnerPaybackByDays> paybackByDays; // 按天退费
  private List<InnerPaybackBySemester> paybackBySemesters; // 按学期退费
  private boolean delayCharge;

  public String getName() {
    return name;
  }

  public ExpensePeriodType getPeriodType() {
    return periodType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public List<PeriodDiscount> getPeriodDiscounts() {
    return periodDiscounts;
  }

  public List<IdentDiscount> getIdentDiscounts() {
    return identDiscounts;
  }

  public List<InnerPaybackByDays> getPaybackByDays() {
    return paybackByDays;
  }

  public List<InnerPaybackBySemester> getPaybackBySemesters() {
    return paybackBySemesters;
  }

  public boolean isDelayCharge() {
    return delayCharge;
  }

  public static class IdentDiscount {

    private ExpenseIdentType identType;
    private BigDecimal discountRate;

    public ExpenseIdentType getIdentType() {
      return identType;
    }

    public BigDecimal getDiscountRate() {
      return discountRate;
    }
  }

  public static class PeriodDiscount {

    private ExpensePeriodType periodType;
    private BigDecimal discountRate;

    public ExpensePeriodType getPeriodType() {
      return periodType;
    }

    public BigDecimal getDiscountRate() {
      return discountRate;
    }
  }

  public static class InnerPaybackByDays{
    private Integer pDay; // 天数
    private Integer compareType;// > = <
    private Integer pType; // 金额=1，比例=2
    private Float pValue; // 金额或者比例

    public Integer getpDay() {
      return pDay;
    }

    public Integer getpType() {
      return pType;
    }

    public Integer getCompareType() {
      return compareType;
    }

    public Float getpValue() {
      return pValue;
    }
  }

  public static class InnerPaybackBySemester{
    private Integer semesterType; // 年度请假=1，学期请假=2
    private Integer vacationDays;// 能请多少天假
    private Integer daysCompareType;// > = <
    private Integer vacationCount; // 能请多少次假
    private Integer countCompareType;// > = <
    private Integer pType; // 金额=1，比例=2
    private Float pValue; // 金额或者比例

    public Integer getSemesterType() {
      return semesterType;
    }

    public Integer getVacationDays() {
      return vacationDays;
    }

    public Integer getDaysCompareType() {
      return daysCompareType;
    }

    public Integer getVacationCount() {
      return vacationCount;
    }

    public Integer getCountCompareType() {
      return countCompareType;
    }

    public Integer getpType() {
      return pType;
    }

    public Float getpValue() {
      return pValue;
    }
  }
}
