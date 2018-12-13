package cn.k12soft.servo.startup;

import static cn.k12soft.servo.domain.enumeration.Permission.ALL;

import cn.k12soft.servo.configuration.K12Properties;
import cn.k12soft.servo.configuration.K12Properties.SuperUser;
import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Application;
import cn.k12soft.servo.domain.Course;
import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.Role;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.domain.iclock.IclockDevice;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.ApplicationRepository;
import cn.k12soft.servo.repository.CourseRepository;
import cn.k12soft.servo.repository.GradeRepository;
import cn.k12soft.servo.repository.RoleRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.repository.iclock.IclockDeviceRepository;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class K12ServoEnvInitializer implements CommandLineRunner {

  private final ApplicationRepository applicationRepository;
  private final GradeRepository gradeRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final K12Properties k12Properties;
  private final PasswordEncoder passwordEncoder;
  private final SchoolRepository schoolRepository;
  private final ActorRepository actorRepository;
  private final IclockDeviceRepository iclockDeviceRepository;

  @Autowired
  public K12ServoEnvInitializer(ApplicationRepository applicationRepository,
                                GradeRepository gradeRepository,
                                CourseRepository courseRepository,
                                UserRepository userRepository,
                                RoleRepository roleRepository,
                                K12Properties k12Properties,
                                PasswordEncoder passwordEncoder,
                                SchoolRepository schoolRepository,
                                ActorRepository actorRepository,
                                IclockDeviceRepository iclockDeviceRepository) {
    this.applicationRepository = applicationRepository;
    this.gradeRepository = gradeRepository;
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.k12Properties = k12Properties;
    this.passwordEncoder = passwordEncoder;
    this.schoolRepository = schoolRepository;
    this.actorRepository = actorRepository;
    this.iclockDeviceRepository = iclockDeviceRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    String appName = k12Properties.getAppName();
    Optional<Application> optional = applicationRepository.findByName(appName);
    if (!optional.isPresent()) {
      School school = new School("博顿幼儿园", "默认创建的测试用");
      school = schoolRepository.save(school);
      IclockDevice iclockDevice = new IclockDevice("3918171200770", school);
      iclockDeviceRepository.save(iclockDevice);

      SuperUser superUser = k12Properties.getSuperUser();

      Integer schoolId = school.getId();
      Role role = new Role(schoolId, "root", "super role which has all permissions");
      role.getPermissions().add(ALL);
      role = roleRepository.save(role);

      User user = new User();
      String password = passwordEncoder.encode(superUser.getPassword());
      user.mobile(superUser.getMobile())
        .username(superUser.getUsername())
        .password(password)
        .gender(superUser.getGender())
        .createdAt(Instant.now());
      user = userRepository.save(user);
      Actor actor = new Actor(schoolId, user.getId());
      actor.addType(ActorType.MANAGER);
      actor = actorRepository.save(actor);

      user.addActor(actor);
      userRepository.save(user);
      actor.addRole(role);
      actorRepository.save(actor);

      Grade gradeTuo = new Grade(schoolId, "托班", "tuo");
      Grade gradeSmall = new Grade(schoolId, "小班", "xiao");
      Grade gradeMid = new Grade(schoolId, "中班", "zhong");
      Grade gradeBig = new Grade(schoolId, "大班", "da");
      gradeRepository.save(Lists.newArrayList(gradeTuo, gradeSmall, gradeMid, gradeBig));

      Course gradePrimary = new Course(schoolId, "primary", "primary");
      Course gradeSecondary = new Course(schoolId, "secondary", "secondary");
      Course gradeChildcare = new Course(schoolId, "childcare", "childcare");
      courseRepository.save(Lists.newArrayList(gradePrimary, gradeSecondary, gradeChildcare));

      Application application = new Application(appName);
      applicationRepository.save(application);
    }
  }
}
