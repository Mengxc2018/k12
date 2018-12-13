package cn.k12soft.servo.module.warehouse.warehouseKlass.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.warehouse.warehouseKlass.domian.WarehouseKlass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseKlassRepository extends JpaRepository<WarehouseKlass, Long>{
    List<WarehouseKlass> findAllBySchoolIdAndKlassId(Integer schoolId, Integer klassId);

    List<WarehouseKlass> findBySchoolIdAndKlassIdAndIsbn(Integer schoolId, Integer klassId, String isbn);

    List<WarehouseKlass> findBySchoolIdAndKlassIdAndName(Integer schoolId, Integer klassId, String name);

    WarehouseKlass findAllBySchoolIdAndKlass(Integer schoolId, Klass klass);
}
