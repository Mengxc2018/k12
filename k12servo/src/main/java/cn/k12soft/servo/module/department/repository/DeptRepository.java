package cn.k12soft.servo.module.department.repository;

import cn.k12soft.servo.module.department.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept>{
    Optional<Dept> findOneById(long id);
}
