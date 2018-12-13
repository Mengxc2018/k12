package cn.k12soft.servo.module.account.repository;

import cn.k12soft.servo.module.account.domain.StudentAccountChangeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAccountChangeRecordRepository extends JpaRepository<StudentAccountChangeRecord, Integer> {
    Page<StudentAccountChangeRecord> findByStudentId(int studentId, Pageable pageable);
}
