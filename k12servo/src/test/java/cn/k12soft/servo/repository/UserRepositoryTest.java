package cn.k12soft.servo.repository;

import org.hibernate.bytecode.enhance.internal.tracker.SimpleFieldTracker;
import org.hibernate.bytecode.enhance.spi.EnhancerConstants;

public class UserRepositoryTest {

    public static void main(String[] args) {
        String value = String.format("public String[] %1$s() {%n" +
                        "  if(%3$s == null) {%n" +
                        "    return (%2$s == null) ? new String[0] : %2$s.get();%n" +
                        "  } else {%n" +
                        "    if (%2$s == null) %2$s = new %5$s();%n" +
                        "    %4$s(%2$s);%n" +
                        "    return %2$s.get();%n" +
                        "  }%n" +
                        "}",
                EnhancerConstants.TRACKER_GET_NAME,
                EnhancerConstants.TRACKER_FIELD_NAME,
                EnhancerConstants.TRACKER_COLLECTION_NAME,
                EnhancerConstants.TRACKER_COLLECTION_CHANGED_FIELD_NAME,
                SimpleFieldTracker.class.getName());
        System.out.println(value);
    }
}
