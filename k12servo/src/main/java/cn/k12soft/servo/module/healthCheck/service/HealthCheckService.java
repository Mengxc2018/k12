package cn.k12soft.servo.module.healthCheck.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.enumeration.Physical;
import cn.k12soft.servo.module.healthCheck.domain.HealthCheck;
import cn.k12soft.servo.module.healthCheck.domain.HealthCondition;
import cn.k12soft.servo.module.healthCheck.domain.dto.HealthMorningDTO;
import cn.k12soft.servo.module.healthCheck.domain.dto.HealthNightDTO;
import cn.k12soft.servo.module.healthCheck.domain.dto.HealthNoonDTO;
import cn.k12soft.servo.module.healthCheck.domain.form.HealthMorningForm;
import cn.k12soft.servo.module.healthCheck.domain.form.HealthNightForm;
import cn.k12soft.servo.module.healthCheck.domain.form.HealthNoonForm;
import cn.k12soft.servo.module.healthCheck.reposiitory.HealthCheckRepository;
import cn.k12soft.servo.module.healthCheck.reposiitory.HealthConditionRepository;
import cn.k12soft.servo.module.healthCheck.service.mapper.HealthMorningMapper;
import cn.k12soft.servo.module.healthCheck.service.mapper.HealthNightMapper;
import cn.k12soft.servo.module.healthCheck.service.mapper.HealthNoonMapper;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.repository.AttendanceRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static cn.k12soft.servo.domain.enumeration.Physical.*;

@Service
@Transactional
public class HealthCheckService extends AbstractRepositoryService<HealthCheck, Long, HealthCheckRepository> {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final HealthMorningMapper healthMorningMapper;
    private final HealthNightMapper healthNightMapper;
    private final HealthNoonMapper healthNoonMapper;
    private final WxService wxService;
    private final KlassRepository klassRepository;
    private final HealthConditionRepository healthConditionRepository;

    @Autowired
    protected HealthCheckService(HealthCheckRepository repository, AttendanceRepository attendanceRepository, StudentRepository studentRepository, HealthMorningMapper healthMorningMapper, HealthNightMapper healthNightMapper, HealthNoonMapper healthNoonMapper, WxService wxService, KlassRepository klassRepository, HealthConditionRepository healthConditionRepository) {
        super(repository);
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.healthMorningMapper = healthMorningMapper;
        this.healthNightMapper = healthNightMapper;
        this.healthNoonMapper = healthNoonMapper;
        this.wxService = wxService;
        this.klassRepository = klassRepository;
        this.healthConditionRepository = healthConditionRepository;
    }


    private final Physical.TYPE MORNING = TYPE.MORNING;
    private final Physical.TYPE NOON = TYPE.NOON;
    private final Physical.TYPE NIGHT = TYPE.NIGHT;

    public void createMorning(Actor actor, List<HealthMorningForm> forms, Integer klassId) {
        Instant first = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        for (HealthMorningForm form : forms) {
            // 当天是否体检，已经体检过的不需要再体检
            Collection<HealthCheck> healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(MORNING, actor.getSchoolId(), klassId, form.getStudentId(), first, second);
            if (healthChecks.size() > 0) {
            }else{
                HealthCheck healthCheck = new HealthCheck(
                        studentRepository.findOne(form.getStudentId()),
                        Instant.now(),
                        klassId,
                        actor.getSchoolId(),
                        form.getType(),
                        form.getSpirit(),
                        form.getBody(),
                        form.getSink(),
                        form.getMouth(),
                        form.getOther(),
                        form.getTemperature(),
                        form.getRemark(),
                        false   // 是否发布
                );
                // 保存体检异常的信息
                HealthCondition healthCondition = this.addHealthCondition(healthCheck);
                healthCheck.setHealthCondition(healthCondition);
                getRepository().save(healthCheck);
            }
        }
    }

    public void createNoon(Actor actor, List<HealthNoonForm> forms, Integer klassId) {
        Instant first = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        for (HealthNoonForm form : forms) {
            // 当天是否体检，已经体检过的不需要再体检
            Collection<HealthCheck> healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(NOON, actor.getSchoolId(), klassId, form.getStudentId(), first, second);
            if (healthChecks.size() > 0) {
            }else{
                HealthCheck healthCheck = new HealthCheck(
                        studentRepository.findOne(form.getStudentId()),
                        klassId,
                        actor.getSchoolId(),
                        Instant.now(),
                        form.getType(),
                        form.getSpirit(),
                        form.getBody(),
                        form.getSink(),
                        form.getDinner(),
                        form.getAfternap(),
                        form.getRemark(),
                        false   // 是否发布
                );
                // 保存体检异常的信息
                HealthCondition healthCondition = this.addHealthCondition(healthCheck);
                healthCheck.setHealthCondition(healthCondition);
                getRepository().save(healthCheck);
            }
        }
    }

    public void createNight(Actor actor, List<HealthNightForm> forms, Integer klassId) {
        Instant first = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        for (HealthNightForm form : forms) {
            // 当天是否体检，已经体检过的不需要再体检
            Collection<HealthCheck> healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(NIGHT, actor.getSchoolId(), klassId, form.getStudentId(), first, second);
            if (healthChecks.size() > 0) {
                return;
            }else {
                HealthCheck healthCheck = new HealthCheck(
                        studentRepository.findOne(form.getStudentId()),
                        klassId,
                        actor.getSchoolId(),
                        Instant.now(),
                        form.getType(),
                        form.getSpirit(),
                        form.getBody(),
                        form.getSink(),
                        form.getAddfood(),
                        form.getExcrete(),
                        form.getRemark(),
                        false   // 是否发布
                );
                // 保存体检异常的信息
                HealthCondition healthCondition = this.addHealthCondition(healthCheck);
                healthCheck.setHealthCondition(healthCondition);
                getRepository().save(healthCheck);
            }
        }
    }


    /**
     * 当天是否打卡
     *
     * @param actor
     * @param studentId
     * @return
     */
    public boolean isExist(Actor actor, Integer studentId) {
        boolean a = false;
        LocalDate now = LocalDate.now();
        Integer num = attendanceRepository.countAllByStudentIdAndSchoolIdAndCreatedAt(studentId, actor.getSchoolId(), now);
        if (num > 0) {
            a = true;
        }
        return a;
    }

    public void issue(Actor actor, String ids, Integer klassId, TYPE type) {

        Integer schoolId = actor.getSchoolId();
        Instant first = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        String[] idx = ids.split(",");
        List<HealthCheck> checkList = new ArrayList<>();
        List<HealthCheck> healthCheckWell = new ArrayList<>();
        Collection<Student> students = studentRepository.findByKlassAndIsShow(klassRepository.findOne(klassId), true);
        if (ids.length() != 0) {
            for (String id : idx) {
                HealthCheck healthCheck = getRepository().findOne(Long.parseLong(id));
                // 已发布过的无需再发布
                if (healthCheck.getIsIssue()) {
                    continue;
                }
                healthCheck.setIssue(true);
                getRepository().save(healthCheck);
                checkList.add(healthCheck);
                healthCheckWell.add(healthCheck);
            }

            // 将表单中的学生拿出来，之后匹配到学生后，再去掉，剩下的学生就是正常的学生
            List<Student> studentList = new ArrayList<>();  // remove
            for (HealthCheck healthCheck : checkList) {
                Integer studentIdIntF = healthCheck.getStudent().getId();
                for (Student student : students) {
                    Integer studentIdInt = student.getId();
                    if (studentIdIntF.equals(studentIdInt)) {
                        studentList.add(student);
                        continue;
                    }
                }
            }

    //        将不正常的remove掉，剩下的记为正常
            students.removeAll(studentList);
        }

        // 保存体检正常的学生
        for (Student student : students) {
            HealthCheck healthCheck = getRepository().queryAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(type, schoolId, klassId, student.getId(), first, second);
            if (healthCheck == null){
                healthCheck = new HealthCheck(
                        studentRepository.findOne(student.getId()),
                        klassId,
                        actor.getSchoolId(),
                        Instant.now(),
                        type,
                        SPIRIT.WELL,
                        BODY.WELL,
                        SINK.WELL,
                        ADDFOOD.WELL,
                        EXCRETE.WELL,
                        " ",
                        true // 是否发布
                );
            }else if (healthCheck.getIsIssue()==false) {
                healthCheck.setIssue(true);
            }else {
                continue;
            }
            getRepository().save(healthCheck);
            healthCheckWell.add(healthCheck);
        }

        healthCheckWell.addAll(checkList);

        if (healthCheckWell.size() > 0) {
            ExecutorService executor = Executors.newCachedThreadPool();
            Future<Double> future = executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    wxService.sendSysMassage(actor, healthCheckWell, klassId);
                    return null;
                }
            });
        }
    }

    public Map<String, Object> findUnIssue(Actor actor, TYPE type, LocalDate localDate, boolean issue, Integer klassId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        Instant first = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        Collection<HealthCheck> healthChecks = new ArrayList<>();
        if (type != null) {
            healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndIssueAndCreatedAtBetween(type, actor.getSchoolId(), klassId, issue, first, second);
            map = healthSwitch(healthChecks, type);
        } else {
            healthChecks = getRepository().findAllBySchoolIdAndKlassIdAndIssueAndCreatedAtBetween(actor.getSchoolId(), klassId, issue, first, second);
            map.put("health", healthChecks);
        }
        return map;
    }


    public Map<String, Object> getAllByCondition(Actor actor, TYPE type, AttendPeriodStat.PeriodType periodType, LocalDate date, Integer studentId, Integer klassId) {
        Integer schoolId = actor.getSchoolId();
        Map<String, Object> map = new HashMap<>();
        Collection<HealthCheck> healthChecks = new ArrayList<>();
        date = date == null ? LocalDate.now() : date;
        Instant first = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        // 如果periodType不为空，重新计算周期时间并赋值给first、second。如果为空，则计算一天的
        if (periodType != null) {
            Pair<LocalDate, LocalDate> pair = periodType.toPeriodRange(date);
            first = pair.getFirst().atStartOfDay().toInstant(ZoneOffset.UTC);
            second = pair.getSecond().atStartOfDay().toInstant(ZoneOffset.UTC);
        }

        // periodType为空 studentId 为空,  按照天查询。如果periodType不为空，按照周、月查询
        if (periodType == null && studentId == null) {
            healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndCreatedAtBetween(type, schoolId, klassId, first, second);
            map = healthSwitch(healthChecks, type);
        }

        // perionType为空 studentId 不为空
        if (periodType == null && studentId != null) {
            healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(type, schoolId, klassId, studentId, first, second);
            map = healthSwitch(healthChecks, type);
        }
        return map;
    }

    public Map<String, Object> healthSwitch(Collection<HealthCheck> healthChecks, Physical.TYPE type) {
        Map<String, Object> map = new HashMap<>();
        switch (type) {
            case MORNING:
                Collection<HealthMorningDTO> morningDTOS = healthMorningMapper.toDTOs(healthChecks);
                map.put("healthMorning", morningDTOS);
                break;
            case NOON:
                Collection<HealthNoonDTO> noonDTOS = healthNoonMapper.toDTOs(healthChecks);
                map.put("healthNoon", noonDTOS);
                break;
            case NIGHT:
                Collection<HealthNightDTO> nightDTOS = healthNightMapper.toDTOs(healthChecks);
                map.put("healthNight", nightDTOS);
                break;
        }
        return map;
    }

    public Collection<Student> findUnChecked(Actor actor, Integer klassId, TYPE type) {
        Collection<Student> studentsRemove = new ArrayList<>();
        Instant first = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        List<Student> studentList = studentRepository.findByKlassAndIsShow(klassRepository.findOne(klassId), true);

        // 早午晚体检情况
        Collection<HealthCheck> healthChecks = getRepository().findAllByTypeAndSchoolIdAndKlassIdAndCreatedAtBetween(type, actor.getSchoolId(), klassId, first, second);

        if (healthChecks.size() != 0){
            for (HealthCheck healthCheck : healthChecks){
                Integer studentIdIntH = healthCheck.getStudent().getId();
                for (Student student : studentList){
                    Integer studentIdInt = student.getId();
                    if (studentIdIntH.equals(studentIdInt)) {
                        studentsRemove.add(student);
                        continue;
                    }
                }
            }
            studentList.removeAll(studentsRemove);
        }
        return studentList;
    }

    public void deleteChecked(String ids) {
        String[] idx = ids.split(",");
        for (String id : idx) {
            getRepository().delete(Long.parseLong(id));
        }
    }

    public void updateMorning(Integer id, HealthMorningForm health) {
        HealthCheck hm = getRepository().findOne(Long.parseLong(id.toString()));
        if (health.getBody() != null) {
            hm.setBody(health.getBody());
        }
        if (health.getMouth() != null) {
            hm.setMouth(health.getMouth());
        }
        if (health.getSpirit() != null) {
            hm.setSpirit(health.getSpirit());
        }
        if (health.getSink() != null) {
            hm.setSink(health.getSink());
        }
        if (health.getOther() != null) {
            hm.setOther(health.getOther());
        }
        if (!Strings.isNullOrEmpty(health.getRemark())) {
            hm.setRemark(health.getRemark());
        }
        hm.setTemperature(health.getTemperature());
        getRepository().save(hm);
    }

    public void updateNoon(Integer id, HealthNoonForm health) {
        HealthCheck hm = getRepository().findOne(Long.parseLong(id.toString()));
        if (health.getBody() != null) {
            hm.setBody(health.getBody());
        }
        if (health.getSpirit() != null) {
            hm.setSpirit(health.getSpirit());
        }
        if (health.getSink() != null) {
            hm.setSink(health.getSink());
        }
        if (health.getDinner() != null) {
            hm.setDinner(health.getDinner());
        }
        if (health.getAfternap() != null) {
            hm.setAfternap(health.getAfternap());
        }
        if (!Strings.isNullOrEmpty(health.getRemark())) {
            hm.setRemark(health.getRemark());
        }
        getRepository().save(hm);
    }

    public void updateNightn(Integer id, HealthNightForm health) {
        HealthCheck hm = getRepository().findOne(Long.parseLong(id.toString()));
        if (health.getBody() != null) {
            hm.setBody(health.getBody());
        }
        if (health.getSpirit() != null) {
            hm.setSpirit(health.getSpirit());
        }
        if (health.getSink() != null) {
            hm.setSink(health.getSink());
        }
        if (health.getAddfood() != null) {
            hm.setAddfood(health.getAddfood());
        }
        if (health.getExcrete() != null) {
            hm.setExcrete(health.getExcrete());
        }
        if (!Strings.isNullOrEmpty(health.getRemark())) {
            hm.setRemark(health.getRemark());
        }
        getRepository().save(hm);
    }

    public void deleteById(String ids) {
        String[] idx = ids.split(",");
        for (String id : idx){
            getRepository().delete(getRepository().findOne(Long.parseLong(id)));
        }
    }

    public void updateOne(Actor actor, String ids) {
        String[] idx = ids.split(",");
        for (String id : idx){
            HealthCheck healthCheck = getRepository().findOne(Long.parseLong(id));
            healthCheck.setIssue(true);
            getRepository().save(healthCheck);
        }
    }

    public Map<String, Object> findByDateAndKlass(Actor actor, Physical typel, Integer klassId, LocalDate date) {
        Integer schoolId = actor.getSchoolId();
        Instant first = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        Map<String, Object> map = new LinkedHashMap<>();
        List<Integer> students = typel == null ? getRepository().findAllStudentIdByKlassAndCreatedAtBetween(klassId, schoolId, true, first, second)
                : this.getRepository().findAlLBySchoolIdAndKlassIDAndIssueAndTypeAndCreatedAtBetween(schoolId, klassId, true, typel, first, second);
        Iterator<Integer> idsInt = students.iterator();
        while(idsInt.hasNext()){
            Integer studentId = idsInt.next();
            Collection<HealthCheck> healthCheckList = getRepository().findAllBySchoolIdAndKlassIdAndStudentIdAndIssueAndCreatedAtBetween(schoolId, klassId, studentId, true, first, second);
            Map<String, Object> mapval = new LinkedHashMap<>();
            String name = "";
            for (HealthCheck healthCheck : healthCheckList){
                Physical.TYPE type = healthCheck.getType();
                switch (type){
                    case MORNING:
                        mapval.put("MORNING", healthMorningMapper.toDTO(healthCheck));
                        break;
                    case NOON:
                        mapval.put("NOON", healthNoonMapper.toDTO(healthCheck));
                        break;
                    case NIGHT:
                        mapval.put("NIGHT", healthNightMapper.toDTO(healthCheck));
                        break;
                }
                name = healthCheck.getStudent().getName();
            }
            map.put(name, mapval);
        }
        return map;
    }

    /**
     * 保存异常信息症状
     * @param healthCheck
     * @return
     */
    public HealthCondition addHealthCondition(HealthCheck healthCheck){

        Student student = studentRepository.findOne(healthCheck.getStudent().getId());
        Integer schoolId = healthCheck.getSchoolId();
        Klass klass = student.getKlass();
        TYPE type = healthCheck.getType();
        String healthMsg = "";  // 异常症状
        String advice = "";     // 医嘱
        String remark = healthCheck.getRemark();     // 备注

        // 精神
        if (healthCheck.getSpirit() != null){
            switch (healthCheck.getSpirit()){
                case DOLDRUMS:
                    healthMsg += "精神不振，";
                    break;
                case SAG:
                    healthMsg += "萎靡，";
                    break;
                case TOOEXCITED:
                    healthMsg += "过于兴奋，";
                    break;
                case TOOAGITATED:
                    healthMsg += "过于烦躁，";
                    break;
            }
        }

        // 身体
        if (healthCheck.getBody() != null){
            switch (healthCheck.getBody()){
                case COUGH:
                    healthMsg += "咳嗽，";
                    break;
                case RHINORRHEA:
                    healthMsg += "流鼻涕，";
                    break;
                case FEVERHIGH:
                    healthMsg += "高烧，";
                    break;
                case EVERLOWF:
                    healthMsg += "低烧，";
                    break;
                case DIARRHEA:
                    healthMsg += "腹泻，";
                    break;
            }
        }
        // 皮肤
        if (healthCheck.getSink() != null){
            SINK sink = healthCheck.getSink();          // 皮肤
            switch (healthCheck.getSink()){
                case RASH:
                    healthMsg += "皮疹，";
                    break;
                case ALLERGY:
                    healthMsg += "过敏，";
                    break;
                case BODYHURT:
                    healthMsg += "身体外伤，";
                    break;
                case FACEHURT:
                    healthMsg += "脸部外伤，";
                    break;
                case MOSQUITOHURT:
                    healthMsg += "蚊虫叮咬，";
                    break;
            }
        }
        // 正餐
        if (healthCheck.getDinner() != null){
            switch (healthCheck.getDinner()){
                case LESSEATTING:
                    healthMsg += "饭吃的较少，";
                    break;
                case LESSVEGETABLE:
                    healthMsg += "蔬菜吃的少，";
                    break;
                case LESSMEAT:
                    healthMsg += "肉类吃的少，";
                    break;
                case TEACHERHELP:
                    healthMsg += "需要老师喂，";
                    break;
            }
        }
        // 午睡
        if (healthCheck.getAfternap() != null){
            switch (healthCheck.getAfternap()){
                case LESS:
                    healthMsg += "少于30分钟，";
                    break;
                case NOSELEEP:
                    healthMsg += "没睡，";
                    break;
            }
        }
        // 加餐
        if (healthCheck.getAddfood() != null){
            switch (healthCheck.getAddfood()) {
                case LESSDRINK:
                    healthMsg += "饮品喝的少，";
                    break;
                case LESSFRUIT:
                    healthMsg += "水果吃的少，";
                    break;
                case LESSMEAT:
                    healthMsg += "肉类吃的少，";
                    break;
                case MEDICINE:
                    healthMsg += "药已吃，";
                    break;
            }
        }
        // 大小便排泄
        if (healthCheck.getExcrete() != null){
            switch (healthCheck.getExcrete()) {
                case YELLO:
                    healthMsg += "小便黄，";
                    break;
                case LESS:
                    healthMsg += "小便少，";
                    break;
                case CONSTIPATION:
                    healthMsg += "便秘，";
                    break;
                case DIARRHEA:
                    healthMsg += "拉稀，";
                    break;
            }
        }
        // 口腔
        if (healthCheck.getMouth() != null){
            switch (healthCheck.getMouth()) {
                case HERPAS:
                    healthMsg += "疱疹，";
                    break;
                case ULCERATION:
                    healthMsg += "溃疡，";
                    break;
                case THROATRED:
                    healthMsg += "咽部发红，";
                    break;
            }
        }
        // 口腔
        if (healthCheck.getOther() != null) {
            switch (healthCheck.getOther()) {
                case MEDICINE:
                    healthMsg += "携带药物，";
                    break;
                case DANGEROUS:
                    healthMsg += "携带危险物品，";
                    break;
                case NOTHING:
                    healthMsg += "无，";
                    break;
            }
        }
        healthMsg = healthMsg.length() == 0 ? "各方面正常" : healthMsg.substring(0, healthMsg.length() - 1);
        HealthCondition healthCondition = new HealthCondition(
                type,
                klass,
                student,
                healthMsg,
                "",
                advice,
                remark,
                schoolId,
                Instant.now()
        );
        return this.healthConditionRepository.save(healthCondition);

    }

    public List<HealthCondition> findHealthConditionByKlass(Actor actor, Integer klassId, LocalDate localDate) {
        Klass klass = this.klassRepository.findOne(klassId);
        Integer schoolId = actor.getSchoolId();
        Instant first = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return this.healthConditionRepository.findBySchoolIdAndKlassAndCreatedAtBetween(schoolId, klass, first, second);
    }
}
