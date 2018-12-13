package cn.k12soft.servo.module.account.repository;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAccountRepository extends JpaRepository<StudentAccount, Integer> {
    StudentAccount findByStudentId(Integer studentId);

    void deleteByStudent(Student student);
}
