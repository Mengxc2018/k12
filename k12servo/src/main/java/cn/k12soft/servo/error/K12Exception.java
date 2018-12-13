package cn.k12soft.servo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class K12Exception extends RuntimeException {

  public K12Exception(String message) {
    super(message);
  }

  public K12Exception(String message, Throwable ex) {
    super(message, ex);
  }
}
