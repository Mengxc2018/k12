package cn.k12soft.servo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/11.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends K12Exception {

  private final Class<?> entityType;
  private final Object identity;

  public EntityNotFoundException(Class<?> entityType, Object identity) {
    super(String.format("Entity:%s(%s) not found", entityType.getSimpleName(), identity));
    this.entityType = entityType;
    this.identity = identity;
  }

  public Class<?> getEntityType() {
    return entityType;
  }

  public Object getIdentity() {
    return identity;
  }
}
