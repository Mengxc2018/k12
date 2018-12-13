package cn.k12soft.servo.module.expense.domain;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
public class PaybackAlias {

  private static final Map<String, String> ALIAS;

  static {
    ImmutableMap.Builder<String, String> alias = ImmutableMap.builder();
    alias.put("出勤日", "attendedDays")
      .put("缺勤日", "absentDays")
      .put("出勤统计日", "totalDays")
      .put("请假日", "leftDays");
    ALIAS = alias.build();
  }

  public static Map<String, String> getALIAS() {
    return ALIAS;
  }
}
