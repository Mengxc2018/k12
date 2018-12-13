package cn.k12soft.servo.security;

import cn.k12soft.servo.error.K12Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends K12Exception {

  public AuthenticationException(String message) {
    super(message);
  }
}
