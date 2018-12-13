package cn.k12soft.servo.module.errcode.resource;

import cn.k12soft.servo.module.errcode.domain.ErrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.annotation.Resource;
import java.util.Optional;

@Resource
public interface ErrCodeResource extends JpaRepository<ErrCode, Long>, JpaSpecificationExecutor<ErrCode> {
    Optional<ErrCode> findByErrcode(String errcode);
}
