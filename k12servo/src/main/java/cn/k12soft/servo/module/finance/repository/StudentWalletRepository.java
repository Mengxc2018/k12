package cn.k12soft.servo.module.finance.repository;

import cn.k12soft.servo.module.finance.domain.wallet.StudentWallet;
import java.util.Optional;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface StudentWalletRepository extends Repository<StudentWallet, Integer> {

  Optional<StudentWallet> findByStudentId(Integer studentId);
}
