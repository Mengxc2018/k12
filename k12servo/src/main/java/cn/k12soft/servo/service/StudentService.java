package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.GuardianRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.dto.GuardianDTO;
import cn.k12soft.servo.service.dto.StudentDTO;
import cn.k12soft.servo.service.mapper.GuardianMapper;
import cn.k12soft.servo.service.mapper.StudentMapper;
import cn.k12soft.servo.web.form.StudentForm;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService extends AbstractEntityService<Student, Integer> {

  private final KlassService klassService;
  private final UserRepository userRepository;
  private final StudentMapper studentMapper;
  private final GuardianMapper guardianMapper;
  private final GuardianRepository guardianRepository;
  private final ActorRepository actorRepository;

  @Autowired

  public StudentService(KlassService klassService,
                        StudentRepository studentRepository,
                        UserRepository userRepository, StudentMapper studentMapper, GuardianMapper guardianMapper, GuardianRepository guardianRepository, ActorRepository actorRepository) {
    super(studentRepository);
    this.klassService = klassService;
    this.userRepository = userRepository;
      this.actorRepository = actorRepository;
      this.studentMapper = studentMapper;
      this.guardianMapper = guardianMapper;
      this.guardianRepository = guardianRepository;
  }

  private final Logger log = LoggerFactory.getLogger(StudentService.class);

  @Override
  protected StudentRepository getEntityRepository() {
    return (StudentRepository) super.getEntityRepository();
  }

  public List<Student> query(Map<String, String> params) {
    Integer klassId = Integer.parseInt(params.getOrDefault("klassId", "0"));
    if (klassId == 0) {
      return getEntityRepository().findAll();
    }
    return getEntityRepository().findByKlassAndIsShow(new Klass(klassId), true);
  }

  public Collection<Student> getAllUpdate(Map<String, String> params) {
    Integer klassId = Integer.parseInt(params.getOrDefault("klassId", "0"));
    Collection<Student> studentList = getEntityRepository().findByKlassAndIsShowAndIsUpdate(new Klass(klassId), true, true);

    CompletableFuture future = CompletableFuture.supplyAsync(()->{
      for (Student stu : studentList){
        stu.setUpdate(false);
        this.getEntityRepository().save(stu);
      }
      return null;
    });

    return studentList;
  }

  public Student create(StudentForm form) {
    Klass klass = klassService.get(form.getKlassId());
    Student student = new Student(
      klass,
      form.getName(),
      form.getGender(),
      form.getAvatar(),
      form.getPortrait(),
      form.getBirthday(),
      form.getJoinedAt(),
      form.getCardNo());
    student.setShow(true);
    return getEntityRepository().save(student);
  }

  public Student update(Integer studentId, StudentForm form) {
    Student student = get(studentId);
    if (!isNullOrEmpty(form.getName())) {
      student.setName(form.getName());
    }
    if (!isNullOrEmpty(form.getAvatar())) {
      student.setAvatar(form.getAvatar());
    }
    if (form.getGender() != null) {
      student.setGender(form.getGender());
    }
    if (form.getBirthday() != null) {
      student.setBirthday(form.getBirthday());
    }
    if (form.getJoinedAt() != null) {
      student.setJoinedAt(form.getJoinedAt());
    }
    if (form.getCardNo() != null) {
      student.cardNo(form.getCardNo());
    }
    if (!isNullOrEmpty(form.getPortrait())) {
      student.setPortrait(form.getPortrait());
    }
    student.setUpdate(true);
    return getEntityRepository().save(student);
  }

  public Student findByName(String studentName) {
    return getEntityRepository().findByName(studentName);
  }

  public void deleteBy(Integer studentId) {
    Student student = getEntityRepository().findOne(studentId);
    student.setShow(false);
    getEntityRepository().save(student);
  }

  public List<Student> getByName(String studentName) {
    return getEntityRepository().getByName(studentName);
  }

  public Map<String, Object> getAllUpdateBySchool(Integer schoolId) {
    Map<String, Object> map = new HashMap<>();
    // 获取所有修改的学生
      Collection<Student> studentList = this.getEntityRepository().findAllBySchoolIdAndIsUpdate(schoolId, true);
      Collection<StudentDTO> students = this.studentMapper.toDTOs(studentList);
              map.put("student", studentList);

    // 获取所有修改家长
    Collection<Guardian> guardianList = guardianRepository.findAllBySchoolIdAndIsUpdate(schoolId, true);
    Collection<GuardianDTO> users = guardianMapper.toDTOs(guardianList);
    map.put("users", users);

    CompletableFuture future = CompletableFuture.supplyAsync(()->{
      try{
        for (Student student : studentList){
          student.setUpdate(false);
          this.getEntityRepository().save(student);
            for (Guardian guardian : guardianList){
                Actor actor = actorRepository.findOne(guardian.getPatriarchId());
                User user = userRepository.findOne(actor.getUserId());
                user.setUpdate(false);
                this.userRepository.save(user);
            }
        }
      }catch (Exception e){
        log.error(e.toString());
      }
      return null;
    });

    return map;
  }
}
