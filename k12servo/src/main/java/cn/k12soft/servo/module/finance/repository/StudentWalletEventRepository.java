package cn.k12soft.servo.module.finance.repository;

import cn.k12soft.servo.module.finance.domain.wallet.StudentWalletEvent;
import java.util.List;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface StudentWalletEventRepository extends Repository<StudentWalletEvent, Long> {

  List<StudentWalletEvent> findAllByWalletId(Integer walletId);
}
