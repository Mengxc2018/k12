package cn.k12soft.servo.module.supplier.repository;

import cn.k12soft.servo.module.supplier.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier>{
    @Query(value = "select * from suppiler"
            + " ", nativeQuery = true)
    Collection<Supplier> findAllByNameAndContactsAndMobileAndTelephone(String name, String contacts, String mobile, String telephone);
}
