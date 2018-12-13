package cn.k12soft.servo.util;

import java.util.regex.Pattern;

public final class Patterns {

  //Regex for acceptable mobile
  public static final String MOBILE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0-9]|8[0-9]|9[0-9])\\d{8}$";
  private static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_REGEX);
  public static final String USERNAME_REGEX = "[a-zA-Z0-9@._-]{1,16}";
  public static final String PASSWORD_REGEX = "[a-zA-Z0-9@._-!]{1,16}";

  public static boolean isMobileText(String text) {
    return MOBILE_PATTERN.matcher(text).find();
  }

  private Patterns() {
  }
}
