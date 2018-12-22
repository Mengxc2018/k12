package cn.k12soft.servo.module.commmontest;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class CommonTaskTest {


    private final CommonTestService commonTestService;


    @Autowired
    public CommonTaskTest(CommonTestService commonTestService){
        this.commonTestService = commonTestService;
    }

    @ApiOperation("脚本：统计收入接口   【【【千万不要在线上数据库执行！！！】】】")
    @GetMapping("/countIncome")
    public void countIncome(){
        commonTestService.countIncome();
    }

    @ApiOperation("收入支出添加虚拟数据   【【【千万不要在线上数据库执行！！！】】】")
    @PutMapping("/addDateIncomePayout")
    public void addDateIncomePayout(){
        commonTestService.addDateIncomePayout();
    }

    @ApiOperation("手动执行人员流动汇总脚本，此脚本在添加员工流动率虚拟数据的时候就运行过，一般不用手动运行")
    @GetMapping("/rateFlowTask")
    public void rateFlowTask(){
        commonTestService.rateFlowTask();
    }

    // 教师添加人员流动率虚拟数据
    @ApiOperation("教师添加人员流动率虚拟数据, 添加完成后执行一遍汇总到区域的脚本   【【【千万不要在线上数据库执行！！！】】】")
    @GetMapping("/addFolwRate")
    public void rateFolwSchool(){
        commonTestService.rateFolwSchool();
    }

    // 学生员工出勤率添加虚拟数据
    @ApiOperation("学生员工出勤率添加虚拟数据   【【【千万不要在线上数据库执行！！！】】】")
    @GetMapping("/addAttendanceRateDate")
    public void groupsList(){
        commonTestService.groupsList();
    }

    @ApiOperation("员工人员流失率添加虚拟数据   【【【千万不要在线上数据库执行！！！】】】")
    @GetMapping("/addRateFlow")
    public void addRateJoin() {
        commonTestService.addRateJoin();
    }

}
