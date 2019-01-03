package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByName(String studentName);

    List<Student> findByKlassAndIsShow(Klass klass, boolean isShow);

    List<Student> getByName(String studentName);

    Collection<Student> findByKlassAndIsShowAndIsUpdate(Klass klass, boolean isShow, boolean isUpdate);

    Collection<Student> findAllBySchoolIdAndIsShow(Integer schoolId, boolean isUpdate);

    Collection<Student> findAllBySchoolIdAndIsUpdate(Integer schoolId, boolean isUpdate);
}
