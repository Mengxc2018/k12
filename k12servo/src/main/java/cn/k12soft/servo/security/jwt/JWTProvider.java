package cn.k12soft.servo.security.jwt;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import cn.k12soft.servo.configuration.K12Properties;
import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Role;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.security.UserAuthentication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class JWTProvider {

  public static final String TYPE = "type";
  public static final String TYPE_USER = "user";
  public static final String TYPE_ACTOR = "actor";

  private final String secretKey;
  private final long tokenInMs;
  private final long rememberMeTokenInMs;
  private final UserRepository userRepository;
  private final ActorRepository actorRepository;

  @Autowired
  public JWTProvider(K12Properties k12Properties,
                     UserRepository userRepository,
                     ActorRepository actorRepository) {
    this.userRepository = userRepository;
    this.actorRepository = actorRepository;
    K12Properties.Security.Jwt jwt = k12Properties.getSecurity().getJwt();
    this.secretKey = jwt.getSecret();
    this.tokenInMs = 1000 * jwt.getTokenInSeconds();
    this.rememberMeTokenInMs = 1000 * jwt.getRememberMeTokenInSeconds();
  }

  public String createToken(User user,
                            boolean rememberMe) throws JsonProcessingException {
    long ts = System.currentTimeMillis() + (rememberMe ? this.rememberMeTokenInMs : this.tokenInMs);
    Date validity = new Date(ts);
    return Jwts.builder()
      .setSubject(user.getId().toString())
      .claim(TYPE, TYPE_USER)
      .signWith(HS512, secretKey)
      .setExpiration(validity)
      .compact();
  }

  public String createToken(Actor actor,
                            boolean rememberMe) throws JsonProcessingException {
    long ts = System.currentTimeMillis() + (rememberMe ? this.rememberMeTokenInMs : this.tokenInMs);
    Date validity = new Date(ts);
    return Jwts.builder()
      .setSubject(actor.getId().toString())
      .claim(TYPE, TYPE_ACTOR)
      .signWith(HS512, secretKey)
      .setExpiration(validity)
      .compact();
  }

  public Jws<Claims> parse(String token) throws IOException {
    return Jwts.parser()
      .setSigningKey(secretKey)
      .parseClaimsJws(token);
  }

  public Authentication getAuthentication(String token) throws IOException {
    Claims claims = parse(token).getBody();
    Integer subject = Integer.valueOf(claims.getSubject());
    Object type = claims.get(TYPE, String.class);
    if (type.equals(TYPE_USER)) {
      User user = userRepository.findOne(subject);
      return new UserAuthentication(user);
    } else if (type.equals(TYPE_ACTOR)) {
      Actor actor = actorRepository.findOne(subject);
      User user = userRepository.findOne(actor.getUserId());
      Set<GrantedAuthority> authorities = Sets.newHashSet(actor.getTypes());
      actor.getRoles().stream().map(Role::getPermissions).forEach(authorities::addAll);
      JWTPrincipal principal = new JWTPrincipal(actor, user, authorities);
      return new JWTAuthentication(principal);
    }
    throw new IllegalStateException("Unknown type:" + type);
  }
}
