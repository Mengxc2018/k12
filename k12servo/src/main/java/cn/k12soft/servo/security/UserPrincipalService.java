package cn.k12soft.servo.security;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPrincipalService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserPrincipalService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserPrincipal loadUserByUsername(String mobile) throws AuthenticationException {
    User user = userRepository.findByMobile(mobile)
      .orElseThrow(() -> new MobileNotFoundException(mobile));
    return new UserPrincipal(user);
  }
}
