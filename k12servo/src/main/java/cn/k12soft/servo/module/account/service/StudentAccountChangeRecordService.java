package cn.k12soft.servo.module.account.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.StudentAccountOpType;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.domain.StudentAccountChangeRecord;
import cn.k12soft.servo.module.account.repository.StudentAccountChangeRecordRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class StudentAccountChangeRecordService extends AbstractRepositoryService<StudentAccountChangeRecord, Integer, StudentAccountChangeRecordRepository> {

  @Autowired
  protected StudentAccountChangeRecordService(StudentAccountChangeRecordRepository repository) {
    super(repository);
  }

  @Transactional(readOnly = true)
  public StudentAccountChangeRecord findByStudentId(Integer studentId) {
    return getRepository().findOne(studentId);
  }

  public void create(Student student, StudentAccount studentAccount, Integer klassId, Float changeMoney, Actor actor, StudentAccountOpType opType){
//      StudentAccountChangeRecord record = new StudentAccountChangeRecord();
////      record.setStudentAccountId(studentAccount.getId());
//      record.setKlassId(klassId);
//      record.setStudentId(student.getId());
//      record.setChangeMoney(changeMoney);
//      record.setRemainMoney(studentAccount.getMoney());
//      record.setCreatedBy(actor);
//      record.setOpType(opType.getId());
//      record.setCreateAt(Instant.now());
//      save(record);
      create(student.getId(), studentAccount, klassId, changeMoney, actor, opType);
  }

  public void create(int studentId, StudentAccount studentAccount, Integer klassId, Float changeMoney, Actor actor, StudentAccountOpType opType){
      StudentAccountChangeRecord record = new StudentAccountChangeRecord();
//      record.setStudentAccountId(studentAccount.getId());
      record.setKlassId(klassId);
      record.setStudentId(studentId);
      record.setChangeMoney(changeMoney);
      record.setRemainMoney(studentAccount.getMoney());
      if(actor != null) {
          record.setCreatedBy(actor);
      }
      record.setOpType(opType.getId());
      record.setCreateAt(Instant.now());
      save(record);
  }

    public Page<StudentAccountChangeRecord> findByStudentId(int studentId, Pageable pageable) {
      return getRepository().findByStudentId(studentId, pageable);
    }
}
