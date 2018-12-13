package cn.k12soft.servo.module.district.repository;

import cn.k12soft.servo.module.zone.domain.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RegionsRepository extends JpaRepository<Regions, Long>, JpaSpecificationExecutor<Regions> {
    Regions findById(Integer regionId);

    Integer countByCode(String codeStr);

    Collection<Regions> findByName(String name);

    Collection<Regions> findByGroupId(Integer groupsId);

    Regions findByCode(String code);
}
