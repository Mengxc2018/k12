package cn.k12soft.servo.module.district.repository;

import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CitysRepository extends JpaRepository<Citys, Long>, JpaSpecificationExecutor<Citys>{
    Collection<Citys> findByProvinceId(Integer parentId);

    Citys findById(Integer id);

    Integer countByProvinceId(Integer id);

    Integer countByCode(String codeStr);

    Collection<Citys> findByName(String name);

    Citys findByCode(String code);
}
