package cn.k12soft.servo.module.weixin.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liubing on 2018/9/4
 */
@Repository
public interface WeixinPayOrderRepository extends JpaRepository<WeixinPayOrder, Integer> {
    WeixinPayOrder findByOrderId(String orderId);

    List<WeixinPayOrder> findByCreateTimeBetween(long fromTime, long toTime);
}
