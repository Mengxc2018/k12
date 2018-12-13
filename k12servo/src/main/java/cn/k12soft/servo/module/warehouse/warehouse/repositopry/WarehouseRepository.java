package cn.k12soft.servo.module.warehouse.warehouse.repositopry;

import cn.k12soft.servo.module.warehouse.warehouse.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse>{
    Warehouse findByName(String name);

    Warehouse findByIsbn(String isbn);

    Integer countByIsbn(String isbn);

    Integer countByName(String name);

    List<Warehouse> findAllBySchoolId(Integer schoolId);
}
