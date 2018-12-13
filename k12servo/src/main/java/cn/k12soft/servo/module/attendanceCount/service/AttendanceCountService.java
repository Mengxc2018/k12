package cn.k12soft.servo.module.attendanceCount.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.AttendTeacTwoUtils;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.AttendTeacUtils;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.IsAttendacne;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.AttendTeacFourUtils;
import cn.k12soft.servo.module.AttendanceTeacher.service.mapper.AttendTeacMapper;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import cn.k12soft.servo.module.schedulingPerson.service.SchedulingPersonService;
import cn.k12soft.servo.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Service
public class AttendanceCountService {

    private final AttendTeacFourUtils attendTeacUtils;
    private final AttendTeacMapper attendTeacMapper;
    private final SchoolRepository schoolRepository;
    private final AttendTeacTwoUtils attendTeacTwoUtils;
    private final SchedulingPersonService schedulingPersonService;
    private final AttendanceTeacherRepository attendanceTeacherRepository;

    @Autowired
    public AttendanceCountService(AttendTeacFourUtils attendTeacUtils,
                                  AttendTeacMapper attendTeacMapper,
                                  SchoolRepository schoolRepository,
                                  AttendTeacTwoUtils attendTeacTwoUtils,
                                  SchedulingPersonService schedulingPersonService,
                                  AttendanceTeacherRepository attendanceTeacherRepository) {
        this.attendTeacUtils = attendTeacUtils;
        this.attendTeacMapper = attendTeacMapper;
        this.schoolRepository = schoolRepository;
        this.attendTeacTwoUtils = attendTeacTwoUtils;
        this.schedulingPersonService = schedulingPersonService;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
    }

    /**
     * 返回这个月不满勤的时间，还差多少时间满勤
     * 只返回一个时长，单位：秒
     * @param actor
     * @param year
     * @param month
     * @return
     */
    public Long isAttendacne(Actor actor, Integer year, Integer month) {

        Long time = new Long(0);
        Integer actorId = actor.getId();
        Integer schoolId = actor.getSchoolId();

        LocalDate date = LocalDate.of(year, month, 1);

        School school = schoolRepository.findOne(actor.getSchoolId());
        Long first = null;
        Long second = null;

        if (school.getFormDate() != null || school.getToDate() != null){
            Integer form = school.getFormDate();
            Integer to = school.getToDate();
            first = AttendTeacUtils.beforeOneMonth(date.withDayOfMonth(to));
            second = new Long(date.withDayOfMonth(form).toString().replace("-",""));
        }else{
            // 如果当前学校没有安排考核周期，则默认为从上个月1号到上个月最后一天
            LocalDate localDate = LocalDate.now().withDayOfMonth(1);
            Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal1.setTimeInMillis(instant.toEpochMilli());
            cal2.setTimeInMillis(instant.toEpochMilli());
            cal1.add(Calendar.MONTH,   -1);
            cal2.add(Calendar.DAY_OF_MONTH,   -1);
            String date1 = new SimpleDateFormat( "yyyyMMdd").format(cal1.getTime());
            String date2 = new SimpleDateFormat( "yyyyMMdd").format(cal2.getTime());
            first = new Long((String)date1);
            second = new Long((String)date2);
        }
        Collection<AttendTeacDTO> attendList = attendTeacMapper.toDTOs(attendanceTeacherRepository.findBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, first, second));

        SchedulingPerson schedulingPerson = schedulingPersonService.getByUserId(actorId);

        // 判断请假类型
        String type = schedulingPerson.getScheduling().getSchedulingType();
        if (type.equals("1")){
            for (AttendTeacDTO attendTeacDTO : attendList) {
                IsAttendacne attendacneAll = attendTeacUtils.isAttendacneAll(attendTeacDTO, schedulingPerson);
                time += attendacneAll.getTime();
            }
        }
        if (type.equals("2")){
            for (AttendTeacDTO attendTeacDTO : attendList) {
                IsAttendacne attendacneAll = attendTeacTwoUtils.isAttendacneTwo(attendTeacDTO, schedulingPerson);
                time += attendacneAll.getTime();
            }
        }

        return time;
    }

}