package cn.k12soft.servo.module.holidaysWeek.management;

import cn.k12soft.servo.module.holidaysWeek.domain.dto.HolidaysWeekDTO;
import cn.k12soft.servo.module.holidaysWeek.domain.form.HolidaysWeekForm;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/holidaysWeek/management")
public class HolidaysWeekManagement {

    private final HolidaysWeekService holidaysWeekService;

    @Autowired
    public HolidaysWeekManagement(HolidaysWeekService holidaysWeekService) {
        this.holidaysWeekService = holidaysWeekService;
    }

    @ApiOperation("上传法定节假日及周六周日是否上班 表")
    @PostMapping
    public void create(@RequestBody @Valid List<HolidaysWeekForm> form){
         holidaysWeekService.create(form);
    }

    @ApiOperation("返回某个月除周六日跟节假日的天数(一个月工作日的天数)")
    @GetMapping("/monthOfWoekDay")
    public Integer num(@RequestParam(name = "month") Integer month,
                       @RequestParam(name = "year") Integer year){
        return holidaysWeekService.queryOfMonthToWorkDay(month, year);
    }

    @ApiOperation("按年查询")
    @GetMapping
    public List<HolidaysWeekDTO> query(@RequestParam(name = "year", required = false) Integer year){
        return holidaysWeekService.query(year);
    }

    @ApiOperation("按年/月删除")
    @DeleteMapping
    public void delete(@RequestParam(name = "year", required = false) Integer year,
                       @RequestParam(name = "month", required = false) Integer month){
        holidaysWeekService.delete(year, month);

    }

}
