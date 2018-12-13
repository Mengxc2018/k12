package cn.k12soft.servo.configuration;

import cn.k12soft.servo.security.permission.PermissionRequiredSecurityMetadataSource;
import cn.k12soft.servo.security.permission.PermissionRequiredVoter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.util.Assert;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class K12GlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

  private AnnotationAttributes enableMethodSecurity;

  @Override
  protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
    return new PermissionRequiredSecurityMetadataSource();
  }

  @Override
  protected AccessDecisionManager accessDecisionManager() {
    List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
    ExpressionBasedPreInvocationAdvice expressionAdvice = new ExpressionBasedPreInvocationAdvice();
    expressionAdvice.setExpressionHandler(getExpressionHandler());
    if (prePostEnabled()) {
      decisionVoters.add(new PreInvocationAuthorizationAdviceVoter(expressionAdvice));
    }
    decisionVoters.add(new RoleVoter());
    decisionVoters.add(new AuthenticatedVoter());
    decisionVoters.add(new PermissionRequiredVoter());
    return new AffirmativeBased(decisionVoters);
  }

  private boolean prePostEnabled() {
    return enableMethodSecurity().getBoolean("prePostEnabled");
  }

  private AnnotationAttributes enableMethodSecurity() {
    if (enableMethodSecurity == null) {
      EnableGlobalMethodSecurity methodSecurityAnnotation = AnnotationUtils
        .findAnnotation(getClass(), EnableGlobalMethodSecurity.class);
      Assert.notNull(methodSecurityAnnotation,
        EnableGlobalMethodSecurity.class.getName() + " is required");
      Map<String, Object> methodSecurityAttrs = AnnotationUtils
        .getAnnotationAttributes(methodSecurityAnnotation);
      this.enableMethodSecurity = AnnotationAttributes.fromMap(methodSecurityAttrs);
    }
    return this.enableMethodSecurity;
  }
}
