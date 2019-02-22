package cn.k12soft.servo.module.holidaysWeek.service;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.holidaysWeek.domain.HolidaysWeek;
import cn.k12soft.servo.module.holidaysWeek.domain.form.HolidaysWeekForm;
import cn.k12soft.servo.module.holidaysWeek.domain.dto.HolidaysWeekDTO;
import cn.k12soft.servo.module.holidaysWeek.service.mapper.HolidaysWeekMapper;
import cn.k12soft.servo.module.holidaysWeek.repository.HolidaysWeekRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class HolidaysWeekService extends AbstractRepositoryService<HolidaysWeek, Long, HolidaysWeekRepository>{

    private final HolidaysWeekMapper mapper;

    @Autowired
    protected HolidaysWeekService(HolidaysWeekRepository repository, HolidaysWeekMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    public void create(List<HolidaysWeekForm> form) {

        for (HolidaysWeekForm f : form){
            HolidaysWeek holidaysWeek = new HolidaysWeek(
                    f.getDate(),
                    f.getName(),
                    f.getIsGone()
            );
            getRepository().save(holidaysWeek);
        }
    }

    public List<HolidaysWeekDTO> query(Integer year) {
        List<HolidaysWeek> lists = getRepository().findByYear(year);
        return mapper.toDTOs(lists);
    }

    public void delete(Integer year, Integer month) {

        if (year != null && month != null){
            getRepository().deleteByYearAndMonth(year, month);
        }else if(year != null){
            getRepository().deleteYear(year);
        }else if(month != null){
            getRepository().deleteMonth(month);
        }else{
            throw new IllegalArgumentException("不能为空，month 或 year必须填一项");
        }

    }

    public Integer queryOfMonthToWorkDay(Integer month, Integer year){

        Integer count = 0;
        Calendar cal = Calendar.getInstance();
        Instant instant = LocalDate.of(year, month, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        cal.setTimeInMillis(instant.toEpochMilli());

        // 获取这个月的天数（月天数）
        Integer monthOfLength = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 计算这个月所有的周六周日天数（周六日天数）
        Integer weekendsOfDay = weekends(year, month);

        // 获得这个月节假日天数及日期
        List<HolidaysWeek> holidaysWeeks = getRepository().queryHolidaysByYearAndMonth(year, month);

        Integer weekendsOfHolidays = 0;
        // 排除节假日中的周六周日（没有周六日的节假日）
        for (HolidaysWeek h : holidaysWeeks){
            cal.setTimeInMillis(h.getDate().toEpochMilli());
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                weekendsOfHolidays ++;
            }
        }
        Integer noWeekendsOfHolidays = holidaysWeeks.size() - weekendsOfHolidays;

        // 获得这个月周六日上班天数（上班的周六日天数）
        Integer weekendsOfDays = getRepository().queryWeekendToWorkByYearAndMonth(year, month);

        // 这个月上班时间 = 月天数 - 周六日天数 - 没有周六日的节假日天数 + 上班的周六日
        count = monthOfLength - weekendsOfDay - noWeekendsOfHolidays + weekendsOfDays;

        return count;
    }

    // 返回某月有几天周六日
    public static Integer weekends(Integer year, Integer month){
        Instant instant = LocalDate.of(year, month, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.toEpochMilli());
        int monthSize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int j = 0;
        for (int i = 0; i < monthSize; i++){
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                j++;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return j;
    }


    /**
     * 是否周六日上班或节假日
     * false 是上班日
     * true 不上班
     * @param instant
     * @return
     */
    public boolean isWeekend(Instant instant){
        boolean is = true;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.UTC));
        cal.setTimeInMillis(instant.toEpochMilli());
        HolidaysWeek holidaysWeek = getRepository().findByDate(instant);
        if (holidaysWeek != null){
            if (holidaysWeek.getIsGone() == VacationTeacherUtil.YES_NO.NO){
                is = true;
            }else {
                is = false;
            }
        }else{
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                is = true;
            }else{
                is = false;
            }
        }
        return is;
    }

    /**
     * 本月应出勤天数
     * @return
     */
    public Integer days(LocalDate localDate){
        if (localDate == null){
            localDate = LocalDate.now();
        }
        int num = localDate.lengthOfMonth();
        Integer one = 0;    // 当月应出勤时间
        for (int i = 1; i <= num; i++){
            localDate = localDate.withDayOfMonth(i);
            Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            boolean isWeek = this.isWeekend(instant);
            if (!isWeek){
                one++;
            }
        }
        return one;
    }
}
