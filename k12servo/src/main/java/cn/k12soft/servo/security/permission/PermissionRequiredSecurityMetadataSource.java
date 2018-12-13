package cn.k12soft.servo.security.permission;

import cn.k12soft.servo.domain.enumeration.Permission;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

public class PermissionRequiredSecurityMetadataSource extends
  AbstractFallbackMethodSecurityMetadataSource {

  protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
    return processAnnotations(clazz.getAnnotations());
  }

  protected Collection<ConfigAttribute> findAttributes(Method method,
                                                       Class<?> targetClass) {
    return processAnnotations(AnnotationUtils.getAnnotations(method));
  }

  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  private List<ConfigAttribute> processAnnotations(Annotation[] annotations) {
    if (annotations == null || annotations.length == 0) {
      return null;
    }
    List<ConfigAttribute> attributes = new ArrayList<>();
    for (Annotation anno : annotations) {
      if (anno instanceof PermissionRequired) {
        PermissionRequired required = (PermissionRequired) anno;
        for (Permission permission : required.value()) {
          attributes.add(new PermissionRequiredAttribute(permission));
        }
        return attributes;
      }
    }
    return null;
  }
}
