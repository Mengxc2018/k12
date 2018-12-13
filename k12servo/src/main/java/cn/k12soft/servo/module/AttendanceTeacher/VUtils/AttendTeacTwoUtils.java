package cn.k12soft.servo.module.AttendanceTeacher.VUtils;

import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 一天打两次卡
 */
@Service
@Transactional
public class AttendTeacTwoUtils {
    private final VacationTeacherRepository vacationTeacherRepository;

    @Autowired
    public AttendTeacTwoUtils(VacationTeacherRepository vacationTeacherRepository) {
        this.vacationTeacherRepository = vacationTeacherRepository;
    }


    boolean is = true;
    Long times = new Long(0);
    List<VacationTeacher> globalList = new ArrayList<>();

    /**
     * 判断传进来的这一天是否全勤
     * @param attendTeacDTO
     * @param schedulingPerson
     * @return
     */
    public IsAttendacne isAttendacneTwo(AttendTeacDTO attendTeacDTO, SchedulingPerson schedulingPerson){

        boolean isTrue = true;
        Long time = new Long(0);
        times = time;
        List<VacationTeacher> list = new ArrayList<>();
        globalList = list;

        IsAttendacne isAttendacne = new IsAttendacne(
                is,
                time,
                new AttendTeacDTO()
        );

        // 上午签到判断
        am(attendTeacDTO, schedulingPerson, isAttendacne);
        if (is){
            pm(attendTeacDTO, schedulingPerson, isAttendacne);
        }

        return isAttendacne;
    }

    public void am(AttendTeacDTO attendTeacDTO, SchedulingPerson schedulingPerson, IsAttendacne isAttendacne){
        LocalTime eAmStart = schedulingPerson.getScheduling().getAmStartTime();
        LocalTime eEnd = schedulingPerson.getScheduling().getPmEndTime();

        // 判断打卡是否为空
        if (attendTeacDTO.getAmStartTime() != null){

            LocalTime amStart = LocalDateTime.ofInstant(attendTeacDTO.getAmStartTime(), ZoneId.systemDefault()).toLocalTime();
            Integer actorId = attendTeacDTO.getActorId();
            Integer attendteacId = attendTeacDTO.getId();

            // 判断早上签到时间是否在规定签到时间之后或者不相等，反之，则签到正常
            if (!(amStart.isBefore(eAmStart) || amStart.equals(eAmStart))) {
                // 判断有没有补签
                VacationTeacher vacationTeacher = vacationTeacherRepository.getByEarliestFormDateAndTeacherId(attendteacId, actorId);
                if (vacationTeacher != null) {
                    globalList.add(vacationTeacher);
                    // 有补签，判断补签开始时间是否在上班开始时间之后或者不相等。反之，则补签开始时间正常
                    LocalTime formTime = LocalDateTime.ofInstant(vacationTeacher.getFormDate(), ZoneId.systemDefault()).toLocalTime();
                    LocalTime toTime = LocalDateTime.ofInstant(vacationTeacher.getToDate(), ZoneId.systemDefault()).toLocalTime();
                    if (!(formTime.isAfter(eAmStart) || formTime.equals(eAmStart))) {
                        // 计算规定签到时间至补签开始时间时长，单位：秒
                        int form = formTime.toSecondOfDay();
                        int eAm = eAmStart.toSecondOfDay();
                        times = times + (form - eAm);
                        isAttendacne.setTime(times);
                        isAttendacne.setIsAll(false);
                    }

                    // 判断补签结束时间是否在早上签到时间之前或不相等，反之结束时间正常
                    if (!(toTime.isAfter(amStart) || toTime.equals(amStart))) {
                        // 计算规定签到时间至补签开始时间时长，单位：秒
                        int to = toTime.toSecondOfDay();
                        int am = amStart.toSecondOfDay();
                        times = times + (to - am);
                        isAttendacne.setTime(times);
                        isAttendacne.setIsAll(false);
                    }
                } else {
                    // 如果没有补签时间，则视为一天旷工
                    int ee = eEnd.toSecondOfDay();
                    int ea = eAmStart.toSecondOfDay();
                    times = times + (ee - ea);
                    isAttendacne.setTime(times);
                    is = false;
                    isAttendacne.setIsAll(false);
                }
            }
        }else{
            // 如果没有补签时间，则视为一天旷工
            int ee = eEnd.toSecondOfDay();
            int ea = eAmStart.toSecondOfDay();
            times = times + (ee - ea);
            isAttendacne.setTime(times);
            is = false;
            isAttendacne.setIsAll(false);
        }
    }

    /**
     * 下午补签
     * @param attendTeacDTO
     * @param schedulingPerson
     * @param isAttendacne
     */
    public void pm(AttendTeacDTO attendTeacDTO, SchedulingPerson schedulingPerson, IsAttendacne isAttendacne){
        if (attendTeacDTO.getPmEndTime() != null) {
            LocalTime pmEnd = LocalDateTime.ofInstant(attendTeacDTO.getPmEndTime(), ZoneId.systemDefault()).toLocalTime();
            LocalTime ePmEnd = schedulingPerson.getScheduling().getPmEndTime();

            // 判断下午签退   下午签退时间是否在规定时间签退时间之前或者不相等。反之，则补签正常
            if (!(pmEnd.isAfter(ePmEnd) || pmEnd.equals(ePmEnd))) {
                // 判断下班之前30分钟开始有没有补签
                LocalDateTime ePmEndTime = LocalDateTime.of(LocalDate.now(), ePmEnd.plusMinutes(-30));
                VacationTeacher vacationTeacher = vacationTeacherRepository.getPmOutByTimeAndActorIdAndAttId(ePmEndTime, attendTeacDTO.getId(), attendTeacDTO.getActorId());
                if (vacationTeacher != null) {
                    globalList.add(vacationTeacher);
                    LocalTime formDate = LocalDateTime.ofInstant(vacationTeacher.getFormDate(), ZoneId.systemDefault()).toLocalTime();
                    LocalTime toDate = LocalDateTime.ofInstant(vacationTeacher.getToDate(), ZoneId.systemDefault()).toLocalTime();
                    // 如果补签开始时间在签到时间结束之后或者不相等。反之，则补签开始时间正常
                    if (!(formDate.isBefore(pmEnd) || formDate.equals(pmEnd))) {
                        int form = formDate.toSecondOfDay();
                        int pmE = pmEnd.toSecondOfDay();
                        times = times + (pmE - form);
                        isAttendacne.setTime(times);
                        isAttendacne.setIsAll(false);
                    }
                    // 如果补签结束时间在规定签到时间之前或者不相等。反之，则补签结束时间相等
                    if (!(toDate.isAfter(ePmEnd) || toDate.equals(ePmEnd))) {
                        int to = toDate.toSecondOfDay();
                        int epe = ePmEnd.toSecondOfDay();
                        times = times + (epe - to);
                        isAttendacne.setTime(times);
                        isAttendacne.setIsAll(false);
                    }
                } else {
                    // 如果没有补签时间，则计算签退时间到规定签退时间
                    int pmE = pmEnd.toSecondOfDay();
                    int ePE = ePmEnd.toSecondOfDay();
                    times = times + (ePE - pmE);
                    isAttendacne.setTime(times);
                    isAttendacne.setIsAll(false);
                }
            }
        }else {
            times = new Long(28800);
            isAttendacne.setTime(times);
            isAttendacne.setIsAll(false );
        }

    }
}
