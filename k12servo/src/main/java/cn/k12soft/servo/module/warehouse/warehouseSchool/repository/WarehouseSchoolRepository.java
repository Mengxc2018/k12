package cn.k12soft.servo.module.warehouse.warehouseSchool.repository;

import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.WarehouseSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseSchoolRepository extends JpaRepository<WarehouseSchool, Long>, JpaSpecificationExecutor<WarehouseSchoolRepository>{

    List<WarehouseSchool> findAllBySchoolId(Integer schoolId);

    WarehouseSchool findAllByNameAndSchoolId(String name, Integer schoolId);

    WarehouseSchool findAllByIsbnAndSchoolId(String isbn, Integer schoolId);

    Integer countByNameAndSchoolId(String name, Integer schoolId);

    Integer countAllByIsbnAndSchoolId(String isbn, Integer schoolId);

    List<WarehouseSchool> findAllBySchoolIdAndSubclassAndSuperclass(Integer schoolId, WarehouseSubclass subclass, WareHouseSuper superClass);


    List<WarehouseSchool> queryAllByIsbnAndSchoolId(String isbn, Integer schoolId);

    List<WarehouseSchool> queryAllByNameAndSchoolId(String name, Integer schoolId);
}
