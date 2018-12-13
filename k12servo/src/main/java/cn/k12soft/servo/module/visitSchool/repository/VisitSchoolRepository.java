package cn.k12soft.servo.module.visitSchool.repository;

import cn.k12soft.servo.domain.enumeration.IsVisit;
import cn.k12soft.servo.module.visitSchool.domain.VisitSchool;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface VisitSchoolRepository extends JpaRepository<VisitSchool, Long>, JpaSpecificationExecutor<VisitSchool> {
    Collection<VisitSchool> findBySchoolIdAndIsVisitAndCreatedAtBetween(Integer schoolId, IsVisit isVisit, Instant first, Instant second, Sort createdAt_);
}
