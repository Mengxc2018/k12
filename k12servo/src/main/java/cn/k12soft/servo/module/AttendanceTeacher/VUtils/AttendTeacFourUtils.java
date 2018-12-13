package cn.k12soft.servo.module.AttendanceTeacher.VUtils;

import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.service.VacationTeacherService;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 一天打四次卡
 */
@Service
@Transactional
public class AttendTeacFourUtils {

    private final VacationTeacherService vacationTeacherService;

    @Autowired
    public AttendTeacFourUtils(VacationTeacherService vacationTeacherService) {
        this.vacationTeacherService = vacationTeacherService;
    }

    // 全局变量
    boolean isTure = true;
    Long times = new Long(0);
    boolean amStartIsNotNull = true; // 上午签到不为空则为true
    boolean pmStartIsNotNull = true; // 下午签到不为空则为true
    List<VacationTeacher> globalList = new ArrayList<>();

    public IsAttendacne isAttendacneAll(AttendTeacDTO attendanceTeacher, SchedulingPerson schedulingPerson){

        isTure = true;              // 更新为true
        Long timesl = new Long(0);    // 更新为0
        times = timesl;
        List<VacationTeacher> list = new ArrayList<>();
        globalList = list;

        IsAttendacne isAttendAll = new IsAttendacne(
                true,
                timesl,
                attendanceTeacher
        );

        // 上午签到
        amStart(attendanceTeacher, schedulingPerson, isAttendAll);

        // 上午签退
        if (amStartIsNotNull){
            amEnd(attendanceTeacher, schedulingPerson, isAttendAll);
        }

        // 下午签到
        pmStart(attendanceTeacher, schedulingPerson, isAttendAll);

        // 下午签退
        if (pmStartIsNotNull){
            pmEnd(attendanceTeacher, schedulingPerson, isAttendAll);
        }

        attendanceTeacher.setList(list);
        isAttendAll = new IsAttendacne(
                isTure,
                times,
                attendanceTeacher
        );
        return isAttendAll;
    }



    /**
     * 上午签到
     * @param attendanceTeacher
     * @param schedulingPerson
     * @param isAttendAll
     */
    public void amStart(AttendTeacDTO attendanceTeacher,
                        SchedulingPerson schedulingPerson,
                        IsAttendacne isAttendAll){

        LocalTime amStartTime = null;
        LocalTime eAmStartTime = schedulingPerson.getScheduling().getAmStartTime();
        LocalTime eAmEndTime = schedulingPerson.getScheduling().getAmEndTime();

        // 判断是否为空----上午签到
        if (attendanceTeacher.getAmStartTime() != null) {

            amStartTime = LocalDateTime.ofInstant(attendanceTeacher.getAmStartTime(), ZoneId.systemDefault()).toLocalTime();

            // 如果规定的签到时间在实际打卡时间之前或者不相等，则视为缺勤，查询是否有补卡
            if (!(amStartTime.isBefore(eAmStartTime) || amStartTime.equals(eAmStartTime))) {

                // 查询最早的补卡时间
                VacationTeacher vacaEarliest = vacationTeacherService.getByEarliestFormDateAndActorId(Integer.valueOf(
                        attendanceTeacher.getId().toString()),
                        attendanceTeacher.getActorId());

                LocalTime formTime = null;
                LocalTime toTime = null;

                if (vacaEarliest != null) {

                    // 添加补签信息
                    globalList.add(vacaEarliest);

                    formTime = LocalDateTime.ofInstant(vacaEarliest.getFormDate(), ZoneId.systemDefault()).toLocalTime();
                    toTime = LocalDateTime.ofInstant(vacaEarliest.getToDate(), ZoneId.systemDefault()).toLocalTime();

                    // 如果补签开始时间在规定上班时间之后或者不相等
                    if (!(formTime.isBefore(eAmStartTime) || formTime.equals(eAmStartTime))) {
                        // 计算时间并且返回false
                        int erlyForm = formTime.toSecondOfDay(); // 秒
                        int aAmStart = eAmStartTime.toSecondOfDay();
                        times = times + (erlyForm - aAmStart);
                        isAttendAll.setTime(times);
                        isAttendAll.setIsAll(false);
                        isTure = false;
                    }

                    // 如果补签结束时间在签到时间之前或者不相等
                    if(!(toTime.isBefore(amStartTime) || toTime.equals(amStartTime))){
                        // 计算时间并且返回false
                        int erlyForm = toTime.toSecondOfDay(); // 秒
                        int amStart = amStartTime.toSecondOfDay();
                        times = times + (erlyForm - amStart);
                        isAttendAll.setTime(times);
                        isAttendAll.setIsAll(false);
                        isTure = false;
                    }

                } else {
                    // 如果补签为空，则直接计算时间并且返回false
                    int amStart = amStartTime.toSecondOfDay(); // 秒
                    int aAmStart = eAmStartTime.toSecondOfDay();
                    times = times + (amStart - aAmStart);
                    isAttendAll.setTime(times);
                    isAttendAll.setIsAll(false);
                    isTure = false;
                }
            }
        }else{
            // 如果上午签到为空，则上午视为缺勤，amStartIsNotNull标记为false
            int eAmStart = eAmStartTime.toSecondOfDay();
            int eAmEnd = eAmEndTime.toSecondOfDay();
            times = times + (eAmEnd - eAmStart);
            isAttendAll.setTime(times);
            isAttendAll.setIsAll(false);
            isTure = false;
            amStartIsNotNull = false;
        }
    }

    /**
     * 上午签退
     * @param attendanceTeacher
     * @param schedulingPerson
     * @param isAttendAll
     */
    private void amEnd(AttendTeacDTO attendanceTeacher,
                       SchedulingPerson schedulingPerson,
                       IsAttendacne isAttendAll) {

        LocalTime amEndTime = null;
        LocalTime eAmStartTime = schedulingPerson.getScheduling().getAmStartTime();
        LocalTime eAmEndTime = schedulingPerson.getScheduling().getAmEndTime();

        // 判断是否为空----上午签退
        if (attendanceTeacher.getAmEndTime() != null) {

            amEndTime = LocalDateTime.ofInstant(attendanceTeacher.getAmEndTime(), ZoneId.systemDefault()).toLocalTime();
            LocalDateTime amEndDateTime = LocalDateTime.ofInstant(attendanceTeacher.getAmEndTime(),ZoneId.systemDefault());

            // 如果规定的签退时间在实际打卡时间之后或者不相等，则视为缺勤，查询是否有补卡
            if (!(amEndTime.isAfter(eAmEndTime) || amEndTime.equals(eAmEndTime))) {

                // 查询离签退最早的补卡时间
                LocalDateTime attAmEndTimeEarly = amEndDateTime.plusMinutes(-30);
                VacationTeacher vacaAmOut = vacationTeacherService.getByAmOutAndDateAndActorId( attAmEndTimeEarly, attendanceTeacher.getId(), attendanceTeacher.getActorId());

                LocalTime formTime = null;
                LocalTime toTime = null;

                if (vacaAmOut != null) {

                    // 添加补签信息
                    globalList.add(vacaAmOut);

                    formTime = LocalDateTime.ofInstant(vacaAmOut.getFormDate(), ZoneId.systemDefault()).toLocalTime();
                    toTime = LocalDateTime.ofInstant(vacaAmOut.getToDate(), ZoneId.systemDefault()).toLocalTime();

                    // 如果补签开始时间在规定上班时间之前或者不相等
                    if (!(formTime.isAfter(eAmEndTime) || formTime.equals(eAmEndTime))) {
                        // 计算时间并且返回false
                        int erlyForm = formTime.toSecondOfDay(); // 秒
                        int aAmEnd = eAmEndTime.toSecondOfDay();
                        times = times + (erlyForm - aAmEnd);
                        isAttendAll.setTime(times);
                        isAttendAll.setIsAll(false);
                        isTure = false;
                    }else{
                        // 如果补签结束时间在签到时间之前或者不相等
                        if(!(toTime.isBefore(amEndTime) || toTime.equals(amEndTime))){
                            // 计算时间并且返回false
                            int erlyForm = toTime.toSecondOfDay(); // 秒
                            int amEnd = amEndTime.toSecondOfDay();
                            times = times + (erlyForm - amEnd);
                            isAttendAll.setTime(times);
                            isAttendAll.setIsAll(false);
                            isTure = false;
                        }
                    }
                } else {
                    // 如果补签为空，计算签退时间到规定签退的时长并且返回false
                    int amEnd = amEndTime.toSecondOfDay(); // 秒
                    int aAmEnd = eAmEndTime.toSecondOfDay();
                    times = times + (aAmEnd - amEnd);
                    isAttendAll.setTime(times);
                    isAttendAll.setIsAll(false);
                    isTure = false;
                }
            }
        }
    }

    /**
     * 下午签到
     * @param attendanceTeacher
     * @param schedulingPerson
     * @param isAttendAll
     */
    public void pmStart(AttendTeacDTO attendanceTeacher,
                        SchedulingPerson schedulingPerson,
                        IsAttendacne isAttendAll){

        LocalTime pmStartTime = null;
        LocalTime ePmStartTime = schedulingPerson.getScheduling().getPmStartTime();
        LocalTime ePmEndTime = schedulingPerson.getScheduling().getPmEndTime();

        // 判断是否为空----下午签到
        if (attendanceTeacher.getPmStartTime() != null) {

            pmStartTime = LocalDateTime.ofInstant(attendanceTeacher.getPmStartTime(), ZoneId.systemDefault()).toLocalTime();

            // 如果规定的签到时间在实际打卡时间之前或者不相等，则视为缺勤，查询是否有补卡
            if (!(pmStartTime.isBefore(ePmStartTime) || pmStartTime.equals(ePmStartTime))) {

                // 查询下午最早的补卡时间
                VacationTeacher vacaEarliest = vacationTeacherService.getByPmInFormDateAndActorId(
                        Integer.valueOf(attendanceTeacher.getId().toString()),
                        attendanceTeacher.getActorId());

                LocalTime formTime = null;
                LocalTime toTime = null;

                if (vacaEarliest != null) {

                    // 添加补签信息
                    globalList.add(vacaEarliest);

                    formTime = LocalDateTime.ofInstant(vacaEarliest.getFormDate(), ZoneId.systemDefault()).toLocalTime();
                    toTime = LocalDateTime.ofInstant(vacaEarliest.getToDate(), ZoneId.systemDefault()).toLocalTime();

                    // 如果补签时间在规定上班时间之后或者不相等
                    if (!(formTime.isBefore(ePmStartTime) || formTime.equals(ePmStartTime))) {
                        // 计算时间并且返回false
                        int erlyForm = formTime.toSecondOfDay(); // 秒
                        int aPmStart = ePmStartTime.toSecondOfDay();
                        times = times + (erlyForm - aPmStart);
                        isAttendAll.setTime(times);
                        isAttendAll.setIsAll(false);
                        isTure = false;

                    }else{
                        // 如果补签结束时间在签到时间之前或者不相等
                        if(!(toTime.isBefore(pmStartTime) || toTime.equals(pmStartTime))){
                            // 计算时间并且返回false
                            int erlyForm = toTime.toSecondOfDay(); // 秒
                            int amEnd = pmStartTime.toSecondOfDay();
                            times = times + (erlyForm - amEnd);
                            isAttendAll.setTime(times);
                            isAttendAll.setIsAll(false);
                            isTure = false;
                        }
                    }
                } else {
                    // 如果补签为空，则直接计算时间并且返回false
                    int pmStart = pmStartTime.toSecondOfDay(); // 秒
                    int aPmStart = ePmStartTime.toSecondOfDay();
                    times = times + (pmStart - aPmStart);
                    isAttendAll.setTime(times);
                    isAttendAll.setIsAll(false);
                    isTure = false;
                }
            }
        }else{
            // 如果下午签到为空，则上午视为缺勤，pmStartIsNotNull标记为false
            int ePmStart = ePmStartTime.toSecondOfDay();
            int ePmEnd = ePmEndTime.toSecondOfDay();
            times = times + (ePmEnd - ePmStart);
            isAttendAll.setTime(times);
            isAttendAll.setIsAll(false);
            pmStartIsNotNull = false;
        }
    }

    /**
     * 下午签退
     * @param attendanceTeacher
     * @param schedulingPerson
     * @param isAttendAll
     */
    private void pmEnd(AttendTeacDTO attendanceTeacher,
                       SchedulingPerson schedulingPerson,
                       IsAttendacne isAttendAll) {

        LocalTime pmEndTime = null;
        LocalTime ePmStartTime = schedulingPerson.getScheduling().getPmStartTime();
        LocalTime ePmEndTime = schedulingPerson.getScheduling().getPmEndTime();

        // 判断是否为空----下午签退
        if (attendanceTeacher.getPmEndTime() != null) {

            pmEndTime = LocalDateTime.ofInstant(attendanceTeacher.getPmEndTime(), ZoneId.systemDefault()).toLocalTime();
            LocalDateTime pmEndDateTime = LocalDateTime.ofInstant(attendanceTeacher.getPmEndTime(), ZoneId.systemDefault());

            // 如果规定的签退时间在实际打卡时间之后或者不相等，则视为缺勤，查询是否有补卡
            if (!(pmEndTime.isAfter(ePmEndTime) || pmEndTime.equals(ePmEndTime))) {

                // 查询离签退最早的补卡时间
                LocalDateTime attPmEndTimeEarly = pmEndDateTime.plusMinutes(-30);
                VacationTeacher vacaPmOut = vacationTeacherService.getByPmOutAndDateAndActorId( attPmEndTimeEarly, attendanceTeacher.getId(), attendanceTeacher.getActorId());

                LocalTime formTime = null;
                LocalTime toTime = null;

                if (vacaPmOut != null) {

                    // 添加补签信息
                    globalList.add(vacaPmOut);

                    formTime = LocalDateTime.ofInstant(vacaPmOut.getFormDate(), ZoneId.systemDefault()).toLocalTime();
                    toTime = LocalDateTime.ofInstant(vacaPmOut.getToDate(), ZoneId.systemDefault()).toLocalTime();

                    // 如果补签开始时间在规定上班时间之前或者不相等
                    if (!(formTime.isAfter(ePmEndTime) || formTime.equals(ePmEndTime))) {
                        // 计算时间并且返回false
                        int erlyForm = formTime.toSecondOfDay(); // 秒
                        int aPmEnd = ePmEndTime.toSecondOfDay();
                        times = times + (erlyForm - aPmEnd);
                        isAttendAll.setTime(times);
                        isAttendAll.setIsAll(false);
                        isTure = false;
                    }else{
                        // 如果补签结束时间在签到时间之前或者不相等
                        if(!(toTime.isBefore(pmEndTime) || toTime.equals(pmEndTime))){
                            // 计算时间并且返回false
                            int erlyForm = toTime.toSecondOfDay(); // 秒
                            int pmEnd = pmEndTime.toSecondOfDay();
                            times = times + (erlyForm - pmEnd);
                            isAttendAll.setTime(times);
                            isAttendAll.setIsAll(false);
                            isTure = false;
                        }
                    }
                } else {
                    // 如果补签为空，计算签退时间到规定签退的时长并且返回false
                    int pmEnd = pmEndTime.toSecondOfDay(); // 秒
                    int ePmEnd = ePmEndTime.toSecondOfDay();
                    times = times + (ePmEnd - pmEnd);
                    isAttendAll.setTime(times);
                    isAttendAll.setIsAll(false);
                    isTure = false;
                }
            }
        }
    }
}
