package cn.k12soft.servo.util;

import javax.annotation.Nonnull;
import org.springframework.util.Assert;

public final class Throwables {

  private static final StackTraceElement[] EMPTY_STACKTRACE = new StackTraceElement[0];

  public static <X extends Throwable> X clearStackTrace(@Nonnull X throwable) {
    Assert.notNull(throwable, "throwable");
    throwable.setStackTrace(EMPTY_STACKTRACE);
    return throwable;
  }

  private Throwables() {
  }
}
