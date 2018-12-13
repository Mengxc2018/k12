package cn.k12soft.servo.module.warehouse.warehouseSchoolLog.repository;

import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.domain.WarehouseSchoolLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface WarehouseSchoolAllotLogRepository extends JpaRepository<WarehouseSchoolLog, Long>, JpaSpecificationExecutor<WarehouseSchoolLog>{
    List<WarehouseSchoolLog> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second);

    List<WarehouseSchoolLog> findAllBySchoolIdAndIsbnAndCreatedAtBetween(Integer schoolId, String isbn, Instant first, Instant second);

    List<WarehouseSchoolLog> findAllBySchoolIdAndIsbn(Integer schoolId, String isbn);

    List<WarehouseSchoolLog> findAllBySchoolId(Integer schoolId);
}
