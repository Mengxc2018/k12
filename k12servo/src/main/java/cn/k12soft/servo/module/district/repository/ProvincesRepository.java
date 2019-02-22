package cn.k12soft.servo.module.district.repository;

import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProvincesRepository extends JpaSpecificationExecutor<Provinces>, JpaRepository<Provinces, Long>{
    Provinces findById(Integer id);

    Integer countByCode(String codeStr);

    Collection<Provinces> findByregionId(Integer regionId);

    Collection<Provinces> findByName(String name);

    Provinces findByCode(String code);
}
