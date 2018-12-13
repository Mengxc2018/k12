package cn.k12soft.servo.module.wxLogin.repository;


import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WxUserRepository extends JpaSpecificationExecutor<WxUsers>, JpaRepository<WxUsers, Long>{

    WxUsers findByOpenid(String openid);

    Integer countByMobile(String mobileStr);

    WxUsers findByMobile(String mobile);
}
