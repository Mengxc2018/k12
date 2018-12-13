package cn.k12soft.servo.security.jwt;

import static cn.k12soft.servo.util.HTTPHeaders.BEARER_PREFIX;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JWTFilter extends GenericFilterBean {

  private final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
  private final JWTProvider jwtProvider;

  @Autowired
  public JWTFilter(JWTProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain)
    throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    try {
      Optional<String> hasJwt = resolveToken(httpServletRequest);
      if (hasJwt.isPresent()) {
        Authentication authentication = jwtProvider.getAuthentication(hasJwt.get());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (ExpiredJwtException e) {
      logger.info("Security exception for user:{} message:{}",
        e.getClaims().getSubject(),
        e.getMessage());
      httpServletResponse.setStatus(SC_UNAUTHORIZED);
    }
  }

  public Optional<String> resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION);
    if (hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return Optional.of(bearerToken.substring(7, bearerToken.length()));
    }
    return Optional.empty();
  }
}
