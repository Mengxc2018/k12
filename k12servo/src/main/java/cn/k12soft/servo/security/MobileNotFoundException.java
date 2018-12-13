package cn.k12soft.servo.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
public class MobileNotFoundException extends UsernameNotFoundException {

  public MobileNotFoundException(String msg) {
    super(msg);
  }
}
