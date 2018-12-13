package cn.k12soft.servo.module.visit.repository;

import cn.k12soft.servo.module.visit.domain.VisitParents;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VisitParentsRepository extends JpaSpecificationExecutor<VisitParents>, JpaRepository<VisitParents, Long>{

    Collection<VisitParents> findByVisitSchoolId(Long visitSchoolId, Sort orders);
}
