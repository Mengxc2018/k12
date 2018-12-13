package cn.k12soft.servo.configuration;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import cn.k12soft.servo.configuration.K12Properties.Security.AccessRule;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.security.UserPrincipalService;
import cn.k12soft.servo.security.jwt.JWTConfigurer;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class K12SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final K12Properties k12Properties;
  private final JWTConfigurer jwtConfigurer;
  private final UserRepository userRepository;

  @Autowired
  public K12SecurityConfiguration(K12Properties k12Properties,
                                  JWTConfigurer jwtConfigurer,
                                  UserRepository userRepository) {
    this.k12Properties = k12Properties;
    this.jwtConfigurer = jwtConfigurer;
    this.userRepository = userRepository;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers("/swagger-ui/index.html")
      .antMatchers("/h2-console/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry
      = http.csrf().disable()
      .cors().and()
      .headers().frameOptions().disable().and()
      .sessionManagement()
      .sessionCreationPolicy(STATELESS)
      .and()
      .exceptionHandling()
      .authenticationEntryPoint((req, res, ex) ->
        res.sendError(SC_FORBIDDEN, "Access Denied: no permission")
      )
      .and()
      .apply(jwtConfigurer).and()
      .authorizeRequests();
    applyAuthorizeRequests(registry);
  }

  private void applyAuthorizeRequests(
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
    AccessRule[] accessRules = k12Properties.getSecurity().getAccessRules();
    for (K12Properties.Security.AccessRule accessRule : accessRules) {
      K12Properties.Security.AntMatcher matcher = accessRule.getMatcher();
      registry = registry.antMatchers(matcher.getMethod(), matcher.getAntPatterns())
        .access(accessRule.getAccess());
    }
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService())
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  protected PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10, new SecureRandom());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return new UserPrincipalService(userRepository);
  }
}
