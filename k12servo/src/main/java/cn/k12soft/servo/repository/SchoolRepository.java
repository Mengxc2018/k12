package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@org.springframework.stereotype.Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

    Collection<School> findByCityId(Integer cityId);

    Integer countByCode(String codeStr);

    School findByCode(String code);
}
