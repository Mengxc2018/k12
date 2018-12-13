package cn.k12soft.servo.module.taxRateModel.repository;

import cn.k12soft.servo.module.taxRateModel.domain.TaxRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRateModelRespository extends JpaRepository<TaxRateModel, Long>, JpaSpecificationExecutor<TaxRateModel>{
}
