package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

public class InterestKlassForm {

  @NotNull
  private Integer type;

//  @NotNull
//  private Integer gradeId;

  @NotNull
  @Size(min = 2, max = 32)
  private String name;

  private String description;

  private Integer  lessonCount; //课节数
  private Instant startLesAt; ////费用开始日
  private Instant  endLesAt; ////费用结束日
  private Integer  lesPeriod; //课节长度

  public Integer getType() {
    return type;
  }

//  public Integer getGradeId() {
//    return gradeId;
//  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

    public Integer getLessonCount() {
        return lessonCount;
    }

    public Instant getStartLesAt() {
        return startLesAt;
    }

    public Instant getEndLesAt() {
        return endLesAt;
    }

    public Integer getLesPeriod() {
        return lesPeriod;
    }
}
