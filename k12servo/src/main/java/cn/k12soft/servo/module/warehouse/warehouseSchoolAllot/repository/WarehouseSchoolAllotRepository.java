package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.repository;

import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.WarehouseSchoolAllot;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface WarehouseSchoolAllotRepository extends JpaRepository<WarehouseSchoolAllot, Long>, JpaSpecificationExecutor<WarehouseSchoolAllot>{
    Collection<WarehouseSchoolAllot> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second, Sort isAllot);
}
