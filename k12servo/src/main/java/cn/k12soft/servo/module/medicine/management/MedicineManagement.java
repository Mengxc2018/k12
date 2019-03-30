package cn.k12soft.servo.module.medicine.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.medicine.domain.Medicine;
import cn.k12soft.servo.module.medicine.domain.dto.MedicineFindByDTO;
import cn.k12soft.servo.module.medicine.domain.form.MedicineForm;
import cn.k12soft.servo.module.medicine.service.MedicineService;
import cn.k12soft.servo.module.medicine.service.mapper.MedicineFindByMapper;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/medicine/management")
public class MedicineManagement {

    private final MedicineService medicineService;
    private final MedicineFindByMapper medicineFindByMapper;

    public MedicineManagement(MedicineService medicineService, MedicineFindByMapper medicineFindByMapper) {
        this.medicineService = medicineService;
        this.medicineFindByMapper = medicineFindByMapper;
    }

    @ApiOperation("家长：提交喂药清单")
    @PostMapping("/pCreate")
    public void pCreate(@Active Actor actor,
                            @RequestBody @Valid MedicineForm form){
        this.medicineService.pCerate(actor, form);
    }

    @ApiOperation("获取一条的详情")
    @GetMapping("findOne")
    public Medicine findOne(@Active Actor actor,
                            @RequestParam @Valid Long id){
        return this.medicineService.findOne(actor, id);
    }

    @ApiOperation("家长：查询正在进行的")
    @GetMapping("/findDoneForParent")
    public Collection<Medicine> findDoneForParent(@Active Actor actor,
                             @RequestParam @Valid LocalDate localDate){
        return this.medicineService.findDoneForParent(actor, localDate);
    }

    @ApiOperation("家长：停药，medicineId跟MedicineTimeId，如果MedicineTineId有值，则停一天里的哪一次；如果没有值，则停一天的")
    @PutMapping("/stopMedicine")
    public void stopMedicine(@Active Actor actor,
                             @RequestParam @Valid Integer medicineId,
                             @RequestParam(required = false) @Valid Integer medicineTimeId){
        this.medicineService.stopMedicine(actor, medicineId, medicineTimeId);
    }

    @ApiOperation("教师：查询正在进行的")
    @GetMapping("/findDoneForTeacher")
    public Collection<Medicine> findDoneForTeacher(@Active Actor actor,
                                         @RequestParam(required = false) @Valid Integer klassId,
                                         @RequestParam @Valid LocalDate localDate){
        return this.medicineService.findDoneForTeacher(actor, klassId, localDate);
    }

    @ApiOperation("获取历史记录,周期为月，如果为家长角色，code填0，非家长角色，code填1")
    @GetMapping("findBy")
    public Collection<MedicineFindByDTO> findBy(@Active Actor actor,
                                                @RequestParam @Valid Integer code,
                                                @RequestParam @Valid LocalDate localDate){
        return medicineFindByMapper.toDTOs(this.medicineService.findBy(actor, code, localDate));
    }

    @ApiOperation("服药打卡")
    @PutMapping("/updateMedicineTime")
    public void updateMedicineTime(@Active Actor actor,
                           @RequestParam @Valid Long medicineId,
                           @RequestParam @Valid Long medicineTimeId){
        this.medicineService.updateMedicineTime(actor, medicineId, medicineTimeId);
    }

    @ApiOperation("删除接口，批量删除请将id用英文逗号隔开")
    @DeleteMapping("/deleteBy")
    public void deleteBy(@RequestParam @Valid String ids){
        this.medicineService.deleteBy(ids);
    }

}

