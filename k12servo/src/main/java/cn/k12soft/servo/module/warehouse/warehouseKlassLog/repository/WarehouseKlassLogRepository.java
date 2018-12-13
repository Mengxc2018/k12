package cn.k12soft.servo.module.warehouse.warehouseKlassLog.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.domain.WarehouseKlassLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface WarehouseKlassLogRepository extends JpaRepository<WarehouseKlassLog, Long>, JpaSpecificationExecutor<WarehouseKlassLog>{
    List<WarehouseKlassLog> findAllBySchoolIdAndKlassAndIsbnAndCreatedAtBetween(Integer schoolId, Klass klass, String isbn, Instant first, Instant second);

    List<WarehouseKlassLog> findAllBySchoolIdAndKlassAndCreatedAtBetween(Integer schoolId, Klass klass, Instant first, Instant second);

    List<WarehouseKlassLog> findAllBySchoolIdAndIsbnAndCreatedAtBetween(Integer schoolId, String isbn, Instant first, Instant second);

    List<WarehouseKlassLog> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second);

    List<WarehouseKlassLog> findAllBySchoolIdAndIsbn(Integer schoolId, String isbn);

    List<WarehouseKlassLog> findAllBySchoolId(Integer schoolId);

    List<WarehouseKlassLog> findAllBySchoolIdAndKlassAndIsbn(Integer schoolId, Klass klass, String isbn);

    List<WarehouseKlassLog> findAllBySchoolIdAndKlass(Integer schoolId, Klass klass);
}
