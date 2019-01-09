package cn.k12soft.servo.module.studentChargeRecord.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Attendance;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.StudentState;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.repository.StudentAccountRepository;
import cn.k12soft.servo.module.charge.domain.ChargePlan;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.charge.repository.ChargePlanRepository;
import cn.k12soft.servo.module.charge.repository.StudentChargePlanRepository;
import cn.k12soft.servo.module.expense.domain.*;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeKlassTotal;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeRecord;
import cn.k12soft.servo.module.studentChargeRecord.repository.StudentChargeKlassTotalRepository;
import cn.k12soft.servo.module.studentChargeRecord.repository.StudentChargeRecordRepository;
import cn.k12soft.servo.repository.AttendanceRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.*;
import java.util.*;

@Service
@Transactional
public class StudentChargeRecordService extends AbstractRepositoryService<StudentChargeRecord, Long, StudentChargeRecordRepository> {

    private final AttendanceRepository attendanceRepository;
    private final HolidaysWeekService holidaysWeekService;
    private final StudentRepository studentRepository;
    private final StudentChargePlanRepository studentChargePlanRepository;
    private final ChargePlanRepository chargePlanRepository;
    private final StudentAccountRepository studentAccountRepository;
    private final StudentChargeKlassTotalRepository studentChargeKlassTotalRepository;
    private final KlassRepository klassRepository;


    @Autowired
    protected StudentChargeRecordService(StudentChargeRecordRepository repository, AttendanceRepository attendanceRepository,
                                         HolidaysWeekService holidaysWeekService, StudentRepository studentRepository, StudentChargePlanRepository studentChargePlanRepository, ChargePlanRepository chargePlanRepository,
                                         StudentAccountRepository studentAccountRepository, StudentChargeKlassTotalRepository studentChargeKlassTotalRepository, KlassRepository klassRepository) {
        super(repository);
        this.attendanceRepository = attendanceRepository;
        this.holidaysWeekService = holidaysWeekService;
        this.studentRepository = studentRepository;
        this.studentChargePlanRepository = studentChargePlanRepository;
        this.chargePlanRepository = chargePlanRepository;
        this.studentAccountRepository = studentAccountRepository;
        this.studentChargeKlassTotalRepository = studentChargeKlassTotalRepository;
        this.klassRepository = klassRepository;
    }


    public List<StudentChargeRecord> findStudentChargeRecord(Actor actor, Integer klassId, LocalDate formDate, LocalDate toDate) {
        List<StudentChargeRecord> studentChargeRecords = new ArrayList<>();
        LocalDate localDateNow = LocalDate.now();
        Instant instantNow = Instant.now();
        Integer schoolId = actor.getSchoolId();

        Instant first = formDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = toDate.plusDays(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);

        boolean isgone = true;
        Collection<Student> students = studentRepository.findAllBySchoolIdAndKlassIdAndIsShow(schoolId, klassId, true);

        // 判断月份,结束时间是否为本月，如果是本月，则处理本月的信息
        if (!localDateNow.getMonth().equals(toDate.getMonth())) {
            isgone = false;
        }

        // 获取周期内的应出勤天数
        Integer days = holidaysWeekService.firstDayToNow(instantNow);

        // 这个月
        if (isgone) {
            for (Student student : students) {

                Integer studentId = student.getId();

                Optional<StudentChargeRecord> studentChargeRecordOpt = this.getRepository().findBySchoolIdAndMonth(schoolId, studentId, formDate);

                StudentAccount studentAccount = studentAccountRepository.findByStudentId(studentId);
                if (studentAccount == null){
                    studentAccount = new StudentAccount( student, 0f);
                    this.studentAccountRepository.save(studentAccount);
                }

                StudentChargeRecord scr = studentChargeRecordOpt.isPresent()
                        ? studentChargeRecordOpt.get()
                        : new StudentChargeRecord(student.getId(), student.getKlass().getId(), student.getJoinedAt(), schoolId, Instant.now());

                // studentChargeIds，缴费id
                String studentChargeIds = StringUtils.isEmpty(scr.getStudentChargeIds()) ? "" : scr.getStudentChargeIds();

                // 保育教育费
                Float feeEducation = scr.getFeeEducation() == null ? 0f : scr.getFeeEducation();
                // 伙食费
                Float feeFood = scr.getFeeFood() == null ? 0f : scr.getFeeFood();
                // 其他
                Float feeOther = scr.getFeeOther() == null ? 0f : scr.getFeeOther();
                // 收费合计
                Float feeTotal = scr.getFeeTotal() == null ? 0f : scr.getFeeTotal();

                // 伙食费跟教育费总和
                Float feeTotalFoodEdu = scr.getFeeTotalFoodEdu() == null ? 0 : scr.getFeeTotalFoodEdu();

                // 余额（上个月的余额）
                Float balance = scr.getBalance() == null ? 0f : scr.getBalance();
                // 保育教育缺勤退费
                Float deductLost = scr.getDeductLost() == null ? 0f : scr.getDeductLost();
                // 伙食费缺勤退费
                Float deductFood = scr.getDeductFood() == null ? 0f : scr.getDeductFood();
                // 其他退费
                Float deductOther = scr.getDeductOther() == null ? 0f : scr.getDeductOther();
                // 退园退费
                Float deductLeave = scr.getDeductLeave() == null ? 0f : scr.getDeductLeave();
                // 退费合计
                Float deductTotal = scr.getDeductTotal() == null ? 0f : scr.getDeductTotal();

                // 实收金额
                Float actual = scr.getActual() == null ? 0f : scr.getActual();
                // 实收金额，收入只有伙食费保教费
                Float actualFoodEdu = scr.getActualFoodEdu() == null ? 0f : scr.getActualFoodEdu();

                // 园长
                // 收费经办人


                // ---------------------出勤-->START----------------------

                // 获取当前实际出勤天数
                Collection<Attendance> attendances = this.attendanceRepository.findBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetweenAndCreatdAtGroupBy(schoolId, klassId, studentId, first, second);
                Integer attendDays = attendances.size();

                // 获取缺勤天数
                Integer daysLost = days - attendDays;

                // ---------------------出勤-->END----------------------


                // 获取学生缴费，时间范围为： 月

                List<StudentCharge> studentCharges = this.studentChargePlanRepository.findByStudentIdAndCreateAtBetweenForSql(studentId, first, second);

                for (StudentCharge studentCharge : studentCharges) {

                    String studentChargeId = studentCharge.getId().toString();

                    if (studentChargeIds.contains(studentChargeId)) {
                        continue;
                    } else {
                        studentChargeIds += studentChargeId + ",";
                    }

                    // *****************收入、退费--START*****************

                    ExpenseEntry expenseEntry = studentCharge.getExpenseEntry();
                    String entityName = expenseEntry.getName();
//                按照实际缴费金额计算
                    if (entityName.contains("保育保教") || entityName.contains("保教") || entityName.contains("保育")) {

                        // 收入
                        feeEducation = studentCharge.getMoney();
                        feeTotal += feeEducation;
                        feeTotalFoodEdu += feeEducation;

                        // 支出
                        if (daysLost != 0) {
                            scr = paybackCount(studentCharge, scr, attendDays, formDate, toDate);
                        }
                    } else if (entityName.contains("伙食")) {

                        // 收入
                        feeFood = studentCharge.getMoney();
                        feeTotal += feeFood;
                        feeTotalFoodEdu += feeFood;

                        // 支出
                        if (daysLost != 0) {
                            scr = paybackCount(studentCharge, scr, attendDays, formDate, toDate);
                        }
                    } else {

                        // 收入
                        feeOther += studentCharge.getMoney();
                        feeTotal += feeOther;

                        // 支出
                        if (daysLost != 0) {
                            scr = paybackCount(studentCharge, scr, attendDays, formDate, toDate);
                        }
                    }

                    // *****************收入、退费--END*****************


                    if (daysLost != 0) {
                        // 上个月余额
                        balance = studentCharge.getPaybackMoney() + studentAccount.getPaybackMoney();
                    }

                }
                // 退费合计
                deductTotal = deductTotal + balance + deductFood + deductLeave + deductOther;

                // 实收金额
                actual = feeTotal - deductTotal;
                // 实收金额，收入只有伙食费保教费
                actualFoodEdu = feeTotalFoodEdu - deductTotal;

                scr.setFeeEducation(feeEducation);
                scr.setFeeFood(feeFood);
                scr.setFeeOther(feeOther);
                scr.setFeeTotal(feeTotal);
                scr.setFeeTotalFoodEdu(feeTotalFoodEdu);

                scr.setDaysAttendance(days);
                scr.setDaysLost(daysLost);

                scr.setBalance(balance);
                scr.setDeductLost(deductLost);
                scr.setDeductFood(deductFood);
                scr.setDeductOther(deductOther);
                scr.setDeductLeave(deductLeave);
                scr.setDeductTotal(deductTotal);

                scr.setActual(actual);
                scr.setActualFoodEdu(actualFoodEdu);

                scr.setStudentChargeIds(studentChargeIds);

                this.getRepository().save(scr);
            }
        }

        studentChargeRecords = this.getRepository().findBySchoolIdAndCreateAtBetween(schoolId, first, second);

        return studentChargeRecords;
    }

    /**
     * 退费
     *
     * @param studentCharge
     * @param scr
     * @param attendDays
     * @param formDate
     * @param toDate
     * @return
     */
    public StudentChargeRecord paybackCount(StudentCharge studentCharge, StudentChargeRecord scr, Integer attendDays, LocalDate formDate, LocalDate toDate) {

        // 保育教育费
        Float feeEducation = scr.getFeeEducation() == null ? 0f : scr.getFeeEducation();

        // 保育教育缺勤退费
        Float deductLost = scr.getDeductLost() == null ? 0f : scr.getDeductLost();
        // 伙食费缺勤退费
        Float deductFood = scr.getDeductFood() == null ? 0f : scr.getDeductFood();
        // 其他退费
        Float deductOther = scr.getDeductOther() == null ? 0f : scr.getDeductOther();
        // 退园退费
        Float deductLeave = scr.getDeductLeave() == null ? 0f : scr.getDeductLeave();
        // 退费合计
        Float deductTotal = scr.getDeductTotal() == null ? 0f : scr.getDeductTotal();

        // 当前学生收费项目
        ExpenseEntry expenseEntry = studentCharge.getExpenseEntry();

        // 如果查不到退费规则，默认不退费
        if (expenseEntry.getPaybackBySemesters().size() != 0 || expenseEntry.getPaybackByDays().size() != 0){

            // 按天退费
            if (expenseEntry.getPaybackByDays().size() != 0){

                List<PaybackByDays> paybackByDays = expenseEntry.getPaybackByDays();

                PaybackByDays paybackByDay = paybackByDays.get(0);
                Integer comparetype = paybackByDay.getCompareType();    // > = <
                Integer pType = paybackByDay.getpType();                // 金额=1，比例=2
                Integer pDay = paybackByDay.getpDay();                  // 天数
                Float pValue = paybackByDay.getpValue();                // 金额或者比例

                //        // 按照实际缴费金额计算

                // 扣费方式/按天退费（考勤天数）
                if (expenseEntry.getPaybackByDays().size() > 0) {
                    String entityName = expenseEntry.getName();

                    //            deductLost = payback(comparetype, attendDays, pDay, deductLost, pValue, feeEducation, pType);
                    switch (comparetype) {
                        case 1:  // >
                            break;

                        case 2:  // = 如果实际出勤天数与退费天数相等
                            // 儿童实际出勤天数 等于 退费规则中的天数中时
                            // 退还（比例=2/金额=1）
                            if (attendDays == pDay) {    // 如果实际出勤天数与退费天数相等
                                if (pType == 1) {

                                    // 判断保教费还是伙食费还是其他
                                    if (entityName.contains("保育") || entityName.contains("保教") || entityName.contains("教育")) {
                                        deductLost =pValue;
                                    } else if (entityName.contains("伙食")) {
                                        deductFood = pValue;
                                    } else {
                                        deductOther += pValue;
                                    }

                                } else if (pType == 2) {           // 如果实际出勤天数比退费出勤天数少

                                    // 判断保教费还是伙食费还是其他
                                    if (entityName.contains("保育") || entityName.contains("保教") || entityName.contains("教育")) {

                                        deductLost = deductLost * pValue;
                                    } else if (entityName.contains("伙食")) {
                                        deductFood = deductFood * pValue;
                                    } else {
                                        deductOther = deductOther + deductOther * pValue;
                                    }
                                }
                            }
                            break;

                        case 3:  // < 如果应出勤天数比实际出勤天数少
                            // 儿童实际出勤天数 小于 退费规则中的天数中时
                            // 退还（比例=2/金额=1）
                            if (attendDays < pDay) {
                                if (pType == 1) {
                                    deductLost = pValue;
                                } else if (pType == 2) {
                                    deductLost = feeEducation * pValue;
                                }
                            }
                            break;
                    }

                }
            } else if (expenseEntry.getPaybackBySemesters().size() > 0) {     // 按学期请假退费暂且不需要
                //                VacationSummary vacationSummary = paybackService.getVacationSummary(studentId, formDate, toDate);
                //                PaybackResult paybackResult = paybackService.getPaybackResult(studentService, studentChargePlanService, studentId, formDate, toDate);
                //                PaybackResult p = paybackService.calc(studentCharge, paybackResult, formDate, vacationSummary.getTermArr(), vacationSummary.getTermArr());
                //
                //                System.out.println(p.getStudent().getName() + "<-->" + p.getMoney() + "<-->" + p.getExpenseEntryList().size());
                //                System.out.println(p.getStudent().getName() + "<-->" + p.getMoney() + "<-->" + p.getExpenseEntryList().size());
            }
        }else{
            scr.setDeductFood(0f);
            scr.setDeductFood(0f);
        }

        scr.setDeductFood(deductFood);
        scr.setDeductLost(deductLost);
        scr.setDeductTotal(deductTotal);
        scr.setDeductOther(deductOther);

        // ---------------------扣费-->END----------------------

        return scr;

    }

//    public Float payback(Integer comparetype, Integer attendDays, Integer pDay, Float deductLost, Float pValue, Float feeEducation, Integer pType) {
//        StudentChargeRecord studentChargeRecord = new StudentChargeRecord();
//        switch (comparetype) {
//            case 1:  // >
//                break;
//            case 2:  // = 如果实际出勤天数与退费天数相等
//                // 儿童实际出勤天数 等于 退费规则中的天数中时
//                // 退还（比例=2/金额=1）
//                if (attendDays == pDay) {    // 如果实际出勤天数与退费天数相等
//                    if (pType == 1) {
//                        deductLost = pValue;
//                    } else if (pType == 2) {           // 如果实际出勤天数比退费出勤天数少
//                        deductLost = feeEducation * pValue;
//                    }
//                }
//                break;
//            case 3:  // < 如果实际出勤天数比实际出勤天数少
//                // 儿童实际出勤天数 小于 退费规则中的天数中时
//                // 退还（比例=2/金额=1）
//                if (attendDays < pDay) {
//                    if (pType == 1) {
//                        deductLost = pValue;
//                    } else if (pType == 2) {
//                        deductLost = feeEducation * pValue;
//                    }
//                }
//                break;
//        }
//        return deductLost;
//    }

//    public void countLastMonth() {
//
//        Float feeEducation = 0f;
//        Float feeFood = 0f;
//        Float feeOther = 0f;
//        Float feeTotal = 0f;
//
//        Float deductLost = 0f;
//        Float deductFood = 0f;
//
//        Integer daysLost = 0;
//        Integer attendDays = 0;
//
//        // 上个月的开始结束时间
//        LocalDate formDate = LocalDate.now().plusMonths(-1).with(TemporalAdjusters.firstDayOfMonth());
//        LocalDate toDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
//        Instant lastMonthFirst = formDate.atStartOfDay().toInstant(ZoneOffset.UTC);
//        Instant lastMonthSecond = toDate.atStartOfDay().toInstant(ZoneOffset.UTC);
//
//
//        List<StudentCharge> studentCharges = this.studentChargePlanRepository.findByCreateAtBetween(lastMonthFirst, lastMonthSecond);
//        for (StudentCharge studentCharge : studentCharges) {
//            String studentChageIds = "";
//            Integer studentChargeId = studentCharge.getId();
//            Integer studentId = studentCharge.getStudentId();
//
//            StudentChargeRecord studentChargeRecord = this.getRepository().findByStudentIdAndCreateAtBetween(studentId, lastMonthFirst, lastMonthSecond);
//
//            Student student = studentRepository.findOne(studentId);
//
//            if (studentChargeRecord == null) {
//                studentChargeRecord = new StudentChargeRecord(
//                        studentId,
//                        studentCharge.getKlassId(),
//                        student.getJoinedAt(),
//                        studentCharge.getSchoolId(),
//                        Instant.now()
//                );
//            } else {
//                if (studentChargeRecord.getStudentChargeIds().contains(studentChargeId.toString())) {
//                    continue;
//                } else {
//
//                    feeEducation = studentChargeRecord.getFeeEducation();
//                    feeFood = studentChargeRecord.getFeeFood();
//                    feeOther = studentChargeRecord.getFeeOther();
//                    feeTotal = studentChargeRecord.getFeeTotal();
//
//                    deductFood = studentChargeRecord.getDeductFood();
//                    deductLost = studentChargeRecord.getDeductLost();
//
//                }
//            }
//
//            studentChageIds = studentChargeRecord.getStudentChargeIds() + studentChargeId.toString() + ",";
//
//            //
//            Integer chargePlanId = studentCharge.getPlanId();
//            ChargePlan chargePlan = this.chargePlanRepository.findOne(chargePlanId);
//            String entityName = chargePlan.getExpenseEntry().getName();
//
//            if (entityName.contains("保育保教") || entityName.contains("保教") || entityName.contains("保育")) {
//
//                // 收入
//                feeEducation = studentCharge.getMoney();
//                feeTotal += feeEducation;
//
//                // 支出
//                if (daysLost != 0) {
//                    studentChargeRecord = paybackCount(studentCharge, studentChargeRecord, attendDays, formDate, toDate);
//                }
//            } else if (entityName.contains("伙食")) {
//
//                // 收入
//                feeFood = studentCharge.getMoney();
//                feeTotal += feeFood;
//
//                // 支出
//                if (daysLost != 0) {
//                    studentChargeRecord = paybackCount(studentCharge, studentChargeRecord, attendDays, formDate, toDate);
//                }
//            } else {
//
//                // 收入
//                feeOther += studentCharge.getMoney();
//                feeTotal += feeOther;
//
//                // 支出
//                if (daysLost != 0) {
//                    studentChargeRecord = paybackCount(studentCharge, studentChargeRecord, attendDays, formDate, toDate);
//                }
//            }
//
//            studentChargeRecord = this.leaveSchool(studentChargeRecord);
//
//            // 欠费余额
////            studentCharge.getRemainMoney()
//
//            // 剩余额度、余额
//            studentChargeRecord.setBalance(studentCharge.getPaybackMoney());
//
//            // 实收余额
////            studentChargeRecord.setActual(actual);
//
//            studentChargeRecord.setDaysLost(daysLost);
//            studentChargeRecord.setDaysAttendance(attendDays);
//
//            studentChargeRecord.setFeeEducation(feeEducation);
//            studentChargeRecord.setFeeFood(feeFood);
//            studentChargeRecord.setFeeOther(feeOther);
//            studentChargeRecord.setFeeTotal(feeTotal);
//
//            studentChargeRecord.setDeductLost(deductLost);
//            studentChargeRecord.setDeductFood(deductFood);
//
//            studentChargeRecord.setStudentChargeIds(studentChageIds);
//
//            this.getRepository().save(studentChargeRecord);
//        }
//    }

    /**
     * 退园退费
     *
     * @return
     */
    public StudentChargeRecord leaveSchool(StudentChargeRecord studentChargeRecord) {
        Float money = 0f;
        Student student = this.studentRepository.findOne(studentChargeRecord.getStudentId());
        if (student.getState() == StudentState.LEAVE_SCHOOL) {
            StudentAccount studentAccount = studentAccountRepository.findByStudentId(studentChargeRecord.getStudentId());
            StudentCharge studentCharge = studentChargePlanRepository.findByStudentIdAndLastCreateAt(student.getId());
            money = studentAccount.getMoney() + studentCharge.getRemainMoney(); // 退园的费用 = 学生账户的钱 + 缴费的remainMoney的钱，学生账户的退费金额不计入，在其他地方计入
            studentChargeRecord.setDeductLeave(money);
        }
        return studentChargeRecord;
    }

    public Collection<StudentChargeKlassTotal> findStudentChargeKlassTotal(Actor actor, LocalDate formDate, LocalDate toDate) {
        return this.studentChargeKlassTotalRepository.findAllBySchoolIdAndCreateAtBetween(actor.getSchoolId(), formDate, toDate);
    }


    public void countStudentChargeKlass(StudentCharge studentCharge){

        Integer klassId = studentCharge.getKlassId();
        Integer schoolId = studentCharge.getSchoolId();
        LocalDate one = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate two = LocalDate.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());

        ExpenseEntry entry = studentCharge.getExpenseEntry();
        String entityName = entry.getName();

        StudentChargeKlassTotal sckt = this.studentChargeKlassTotalRepository.findBySchoolIdKlassIdAndCreateAtBetween(schoolId, klassId, one, two);
        if (sckt == null){
            Klass klass = this.klassRepository.findOne(studentCharge.getKlassId());
            sckt = new StudentChargeKlassTotal(studentCharge.getKlassId(), klass.getName(), studentCharge.getSchoolId(), Instant.now());
        }

        String studentChargeIds = StringUtils.isBlank(sckt.getStudentChargeIds()) ? "" : sckt.getStudentChargeIds();
        if (studentChargeIds.contains(studentCharge.getId().toString())){
            return;
        }

        studentChargeIds = studentChargeIds + "," + studentCharge.getId();

        Float feeEducation = sckt.getFeeEducation();
        Float feeFood = sckt.getFeeFood();
        Float feeTotal = sckt.getFeeTotal();
        Float feeAllTotal = sckt.getFeeAllTotal();

        String feeOther = StringUtils.isBlank(sckt.getFeeOther()) ? "" : sckt.getFeeOther();


        if (entityName.contains("保育保教") || entityName.contains("保教") || entityName.contains("保育")) {

            // 收入
            feeEducation += studentCharge.getMoney();
            feeTotal += studentCharge.getMoney();
            feeAllTotal += studentCharge.getMoney();

        } else if (entityName.contains("伙食")) {

            // 收入
            feeFood += studentCharge.getMoney();
            feeTotal += studentCharge.getMoney();
            feeAllTotal += studentCharge.getMoney();

        } else {
            // 收入
             Map<String, Object> feeOtherMap = new HashMap<>();
            JSONObject feeOtherJson = new JSONObject();
            if (!StringUtils.isBlank(feeOther)){
                feeOtherMap = feeOtherJson.fromObject(feeOther);
            }

            // 如果为空的key，添加；如果不为空，先取出来，再累加
            Float feeMoneyFlaot = studentCharge.getMoney();
            if (feeOtherMap.get(entityName) != null){
                Float getMapMoney = Float.valueOf(feeOtherMap.get(entityName).toString());
                feeMoneyFlaot += getMapMoney;
            }
            feeOtherMap.put(entityName, feeMoneyFlaot);

            feeAllTotal += studentCharge.getMoney();
            feeOther = feeOtherMap.toString();

        }

        // 班级在册人数
        if (sckt.getListed() == null || sckt.getListed() < 0){
            Integer klassStudentTotal = studentRepository.findAllBySchoolIdAndKlassIdAndIsShow(schoolId, klassId, true).size();
            sckt.setListed(klassStudentTotal);
        }

        sckt.setFeeFood(feeFood);
        sckt.setFeeEducation(feeEducation);
        sckt.setFeeTotal(feeTotal);
        sckt.setFeeAllTotal(feeAllTotal);
        sckt.setStudentChargeIds(studentChargeIds);

        sckt.setFeeOther(feeOther);

        this.studentChargeKlassTotalRepository.save(sckt);


    }


}
