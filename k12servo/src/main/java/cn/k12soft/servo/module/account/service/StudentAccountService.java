package cn.k12soft.servo.module.account.service;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.repository.StudentAccountRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentAccountService extends AbstractRepositoryService<StudentAccount, Integer, StudentAccountRepository> {

  private final StudentService studentService;

  @Autowired
  protected StudentAccountService(StudentAccountRepository repository, StudentService studentService) {
    super(repository);
    this.studentService = studentService;
  }

  @Transactional(readOnly = true)
  public StudentAccount findByStudentId(Integer studentId) {
    return getRepository().findByStudentId(studentId);
  }

    public void deleteByStudent(Integer studentId) {
      Student student = new Student(studentId);
      getRepository().deleteByStudent(student);
    }
}
