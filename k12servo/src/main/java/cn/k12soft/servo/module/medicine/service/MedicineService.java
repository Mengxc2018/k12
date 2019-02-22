package cn.k12soft.servo.module.medicine.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.feeDetails.repository.MedicineTimeRepository;
import cn.k12soft.servo.module.medicine.domain.Medicine;
import cn.k12soft.servo.module.medicine.domain.MedicineTime;
import cn.k12soft.servo.module.medicine.domain.form.MedicineTimeForm;
import cn.k12soft.servo.module.medicine.domain.form.MedicineForm;
import cn.k12soft.servo.module.medicine.repository.MedicineRepository;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.repository.GuardianRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class MedicineService extends AbstractRepositoryService<Medicine, Long, MedicineRepository>{

    private final StudentRepository studentRepository;
    private final MedicineTimeRepository medicineTimeRepository;
    private final WxService wxService;
    private final GuardianRepository guardianRepository;
    private final UserRepository userRepository;

    protected MedicineService(MedicineRepository repository, StudentRepository studentRepository, MedicineTimeRepository medicineTimeRepository, WxService wxService, GuardianRepository guardianRepository, UserRepository userRepository) {
        super(repository);
        this.studentRepository = studentRepository;
        this.medicineTimeRepository = medicineTimeRepository;
        this.wxService = wxService;
        this.guardianRepository = guardianRepository;
        this.userRepository = userRepository;
    }

    public void pCerate(Actor actor, MedicineForm form) {
        Integer days = form.getDays();      // 服药天数
        Instant executeTime = form.getExecuteTime();    // 执行日期
        Integer schoolId = actor.getSchoolId();
        for (int i = days; i > 0; i--){
            Set<MedicineTime> detailSet = new HashSet<>();
            Iterator<MedicineTimeForm> formIterator = form.getMedicineTimeForms().iterator();
            while(formIterator.hasNext()){
                MedicineTimeForm medicineTimeForm = formIterator.next();
                MedicineTime medicineTime = new MedicineTime(
                        medicineTimeForm.getTime(),
                        false,
                        false
                );
                detailSet.add(medicineTime);

            }

            Medicine medicine = new Medicine(
                    actor.getId(),
                    form.getStuId(),
                    form.getStuName(),
                    form.getMedicineName(),
                    form.getDose(),
                    executeTime,
                    detailSet,
                    form.getDays(),
                    form.getKlassId(),
                    schoolId,
                    form.getRemark(),
                    Instant.now()
            );

            this.getRepository().save(medicine);
            executeTime = executeTime.plus(1, ChronoUnit.DAYS);
        }
//
//
//        Iterator<MedicineTimeForm> iterator = form.getMedicineDetail().iterator();
//        while(iterator.hasNext()){
//            MedicineTimeForm f = iterator.next();
//
//            // 处理药品详情
//            Iterator<MedicineDetailForm> medicineDetailForms = f.getMedicineDetailForms().iterator();
//            Set<MedicineDetail> medicineDetails = new HashSet<>();
//            while(medicineDetailForms.hasNext()){
//                MedicineDetailForm medicineDetailForm = medicineDetailForms.next();
//                MedicineDetail medicineDetail = new MedicineDetail()
//                        .setMedicineName(medicineDetailForm.getMedicineName())
//                        .setDose(medicineDetailForm.getDose());
//                medicineDetails.add(medicineDetail);
//            }
//
//            MedicineTime medicineDetail = new MedicineTime(
//                    f.getTime(),
//                    medicineDetails,
//                    false
//            );
//            detailSet.add(medicineDetail);
//        }
//        Student student = studentRepository.findOne(form.getStuId());
//        Medicine medicine = new Medicine(
//                actor.getId(),
//                student.getId(),
//                form.getStuName(),
//                form.getExecuteTime(),
//                detailSet,
//                false,
//                form.getKlassId(),
//                schoolId,
//                form.getRemark(),
//                Instant.now()
//        );
    }


    public Medicine findOne(Actor actor, Long id) {
        return this.getRepository().findOne(id);
    }

    public Collection<Medicine> findDoneForParent(Actor actor, LocalDate localDate) {
        return this.getRepository().findDoneForParent(actor.getId(), false, false, localDate);
    }

    public Collection<Medicine> findDoneForTeacher(Actor actor, Integer klassId, LocalDate localDate) {
        return this.getRepository().findDoneForTeacher(actor.getId(), klassId, false,false, localDate);
    }

    public Collection<Medicine> findBy(Actor actor, Integer code, LocalDate localDate) {
        Integer schoolId = actor.getSchoolId();
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Sort sort = new Sort(Sort.Direction.ASC, "isOver").and(new Sort(Sort.Direction.ASC, "stuName"));
        Collection<Medicine> medicines =
                code == 0 ? this.getRepository().findAllBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actor.getId(), first, second, sort)
                : this.getRepository().findAllBySchoolIdAndCreatedAtBetween(schoolId, first, second, sort);
        return medicines;
    }

    public void  updateMedicineTime(Actor actor, Long medicineId, Long medicineTimeId) {

        MedicineTime medicineTime = this.medicineTimeRepository.findOne(medicineTimeId);
        if (medicineTime.getIsTake()){
            return ;
        }
        medicineTime.setTake(true);
        medicineTime.setCarryActorId(actor.getId());
        this.medicineTimeRepository.save(medicineTime);

        CompletableFuture completableFuture = CompletableFuture.runAsync(()->{

            boolean isOver = true;
            Medicine medicine = this.getRepository().findOne(medicineId);
            for (MedicineTime ti : medicine.getMedicineTime()){
                if (ti.getIsTake() == false){
                    isOver = false;
                }
            }

            if (isOver){
                medicine.setOver(true);
                medicine.setCarryActorId(actor.getId());
                this.getRepository().save(medicine);
            }

            User user = userRepository.findOne(actor.getUserId());
            Map<String, Object> map = new LinkedHashMap<>();

            map.put("one", "您家宝宝到了服药时间啦，快来看看吧！");
            map.put("two", "宝宝姓名："+medicine.getStuName());
            map.put("three", "发送人："+user.getUsername());

            // 获取家长actor，发送微信推送服务
            Integer studentId = medicine.getStuId();
            wxService.sendMedicine(studentId, map, map.get("one").toString());

        });

    }

    public void deleteBy(String ids) {
        String[] ida = ids.split(",");
        for (String idx : ida){
            Medicine m = this.getRepository().findOne(Long.parseLong(idx));
            this.getRepository().delete(m);
        }
    }

    public void stopMedicine(Actor actor, Integer medicineId, Integer medicineTimeId) {

        if (medicineTimeId != null){
            MedicineTime medicineTime = this.medicineTimeRepository.findOne(Long.parseLong(medicineTimeId.toString()));
            medicineTime.setStop(true);
            this.medicineTimeRepository.save(medicineTime);
            CompletableFuture future = CompletableFuture.runAsync(()->{
                isAllStop(medicineId);
            });
        }else {
            Medicine medicine = this.getRepository().findOne(Long.parseLong(medicineId.toString()));
            medicine.setStop(true);
            Iterator<MedicineTime> medicineTimeIterator = medicine.getMedicineTime().iterator();
            while (medicineTimeIterator.hasNext()){
                MedicineTime medicineTime = medicineTimeIterator.next();
                medicineTime.setStop(true);
            }
            this.getRepository().save(medicine);
        }

    }

    /**
     * 判断是否全部停药，如果一天的几次药停了，则改变这一天的停药状态
     */
    private void isAllStop(Integer medicineId){
        Medicine medicine = this.getRepository().findOne(Long.parseLong(medicineId.toString()));
        Iterator<MedicineTime> medicineTimeIterator = medicine.getMedicineTime().iterator();
        boolean isStop = false;
        int i = 0;
        int j = 0;
        while (medicineTimeIterator.hasNext()){
            MedicineTime medicineTime = medicineTimeIterator.next();
            if (medicineTime.getIsStop()){
                j++;
            }
            i++;
        }
        if(j == i){
            medicine.setStop(true);
        }
        this.getRepository().save(medicine);
    }
}
