package cn.k12soft.servo.module.weeklyRemark.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.weeklyRemark.domain.Weekly;
import cn.k12soft.servo.module.weeklyRemark.domain.form.WeeklyForm;
import cn.k12soft.servo.module.weeklyRemark.repository.WeeklyReopsitory;
import cn.k12soft.servo.repository.GuardianRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@Transactional
public class WeeklyService extends AbstractRepositoryService<Weekly, Long, WeeklyReopsitory>{

    private final KlassRepository klassRepository;
    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;

    @Autowired
    protected WeeklyService(WeeklyReopsitory repository, KlassRepository klassRepository, StudentRepository studentRepository, GuardianRepository guardianRepository) {
        super(repository);
        this.klassRepository = klassRepository;
        this.studentRepository = studentRepository;
        this.guardianRepository = guardianRepository;
    }

    public Weekly created(Actor actor, WeeklyForm form) {
        Integer schoolId = actor.getSchoolId();
        Klass klass = klassRepository.findOne(form.getKlassId());
        Student student = studentRepository.findOne(form.getStudentId());

        TemporalField field = WeekFields.of(Locale.CHINESE).dayOfWeek();
        LocalDate one = LocalDate.now().with(field, 1);
        LocalDate two = LocalDate.now().with(field, 7);
        Instant first = one.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = two.atStartOfDay().toInstant(ZoneOffset.UTC);

        Optional<Weekly> w = this.getRepository().findBySchoolIdAndStudentAndCreatedAtBetween(schoolId, student, first, second);
        if (w.isPresent()){
            throw new IllegalArgumentException("学生 " + student.getName() + " 已创建点评， 请勿重复创建");
        }
        String datePeriod = one.toString().replace("-", "/") + "--" + two.getMonth().getValue() + "/" + two.getDayOfMonth(); // 时间周期，范围为周
        Weekly weekly = new Weekly(
                student,
                datePeriod,
                form.getTcontext(),
                form.getPcontext(),
                false,
                form.getEmotion(),
                form.getDine(),
                form.getSeleep(),
                form.getEnvironment(),
                form.getPartner(),
                form.getSanitation(),
                form.getHealth(),
                form.getSelf(),
                Instant.now(),
                klass,
                schoolId
        );
        return this.getRepository().save(weekly);
    }

    public Weekly parent(Actor actor, Integer id, String message) {
        Weekly weekly = this.getRepository().findOne(Long.parseLong(id.toString()));
        if (!StringUtils.isEmpty(message)){
            weekly.setPContext(message);
            weekly.setpStatus(true);
            return this.getRepository().save(weekly);
        }
        return weekly;
    }

    public List<Weekly> findBy(Actor actor, Integer studentId, Integer klassId, LocalDate date) {
        Integer schoolId = actor.getSchoolId();
        Klass klass = this.klassRepository.findOne(klassId);
        TemporalField temporalField = WeekFields.of(Locale.getDefault()).dayOfWeek();
        Instant first = date.atStartOfDay().with(temporalField, 1).toInstant(ZoneOffset.UTC);
        Instant second = date.atStartOfDay().with(temporalField,7).toInstant(ZoneOffset.UTC);
        return this.getRepository().findAllBySchoolIdAndKlassAndStudentAndCreatedAtBetween(schoolId, klass, this.studentRepository.findOne(studentId), first, second);
    }

    public List<Map<String, Object>> findDate(Actor actor, Integer studentId) {
        Student student = this.studentRepository.findOne(studentId);
        List<Object[]> list = this.getRepository().findCreatedAtByKlassAndStudent(student.getKlass().getId(), student.getId());
        List<Map<String, Object>> lm = new ArrayList<>();

        for (Object[] objs : list){
                Map<String, Object> map = new LinkedHashMap<>();
                Instant instant = ((Timestamp) objs[0]).toInstant();
                map.put("date", LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate().toString());
                map.put("ststus", objs[1]);
                lm.add(map);
        }
        return lm;
    }

    public Integer countUnRead(Actor actor) {
        Collection<Guardian> guardians = this.guardianRepository.findByPatriarchId(actor.getId());
        Integer i = 0;
        for (Guardian guardian : guardians){
            List<Weekly> list = this.getRepository().findAllBySchoolIdAndStudentAndPStatus(actor.getSchoolId(), guardian.getStudent(), false);
            i += list.size();
        }
        return i;
    }
}
