package cn.k12soft.servo.module.wxApplication.repository;

import cn.k12soft.servo.module.wxApplication.domain.WxApplication;
import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WxAppRepository extends JpaRepository<WxApplication, Long>, JpaSpecificationExecutor<WxApplication>{
}
