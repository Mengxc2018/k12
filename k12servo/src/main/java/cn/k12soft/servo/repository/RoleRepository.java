package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Role;
import cn.k12soft.servo.domain.School;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  List<Role> findAllBySchoolId(Integer schoolId);

  Role findBySchoolIdAndName(Integer schoolId, String root);
}
