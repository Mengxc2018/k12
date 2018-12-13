package cn.k12soft.servo.module.holidaysWeek.service.mapper;

import cn.k12soft.servo.module.attendanceCount.domain.dto.EntityMapperToList;
import cn.k12soft.servo.module.holidaysWeek.domain.HolidaysWeek;
import cn.k12soft.servo.module.holidaysWeek.domain.dto.HolidaysWeekDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class HolidaysWeekMapper extends EntityMapperToList<HolidaysWeek, HolidaysWeekDTO> {

    @Override
    protected HolidaysWeekDTO convert(HolidaysWeek holidaysWeek) {
        LocalDate date = LocalDateTime.ofInstant(holidaysWeek.getDate(), ZoneId.systemDefault()).toLocalDate();
        int dayOfMonth = date.getDayOfMonth();
        int monthValue = date.getMonthValue();
        String strDate = String.valueOf(monthValue) + "-" + String.valueOf(dayOfMonth);
        return  new HolidaysWeekDTO(
                holidaysWeek.getId(),
                strDate,
                holidaysWeek.getName(),
                holidaysWeek.getIsGone()
        );
    }
}
