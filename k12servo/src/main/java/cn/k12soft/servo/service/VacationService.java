package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.repository.GuardianRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.repository.VacationRepository;
import cn.k12soft.servo.service.dto.VacationDTO;
import cn.k12soft.servo.service.mapper.VacationMapper;
import cn.k12soft.servo.web.form.VacationForm;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Service
public class VacationService extends AbstractRepositoryService<Vacation, Long, VacationRepository> {

  private final StudentService studentService;
  private final VacationMapper mapper;
  private final GuardianRepository guardianRepository;
  private final UserRepository userRepository;

  @Autowired
  public VacationService(VacationRepository repository,
                         StudentService studentService,
                         VacationMapper mapper, GuardianRepository guardianRepository, UserRepository userRepository) {
    super(repository);
    this.studentService = studentService;
    this.mapper = mapper;
    this.guardianRepository = guardianRepository;
    this.userRepository = userRepository;
  }

  public VacationDTO create(VacationForm form) {
    Student student = studentService.get(form.getStudentId());
    Vacation vacation = new Vacation(student.getId(),
      student.getKlass().getId(),
      form.getReason(),
      form.getDesc(),
      form.getFromDate(),
      form.getToDate(),
      Instant.now());
    vacation = getRepository().save(vacation);
    return mapper.toDTO(vacation);
  }

  public Collection<VacationDTO> query(Integer klassId, LocalDate date) {
    Instant instant = date.atStartOfDay().toInstant(ZoneOffset.UTC);
    return mapper.toDTOs(getRepository().findAllByKlassIdAndCreatedAtBetween(klassId, instant));
  }

  public Collection<VacationDTO> query(Integer klassId, Integer studentId, LocalDate date) {
    Instant startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC);
    Instant endOfDay = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    return mapper.toDTOs(getRepository().findAllByKlassIdAndStudentIdAndCreatedAtBetween(klassId, studentId, startOfDay, endOfDay));
  }

  public Collection<VacationDTO> query(Integer klassId, LocalDate startOfMonth, LocalDate lastOfMonth) {
    return mapper.toDTOs(getRepository().findAllByKlassIdAndCreatedAtBetween(klassId, startOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC), lastOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC)));
  }

  public Collection<VacationDTO> queryByStudentId(Integer studentId, LocalDate startOfMonth, LocalDate lastOfMonth) {
    return mapper.toDTOs(getRepository().findAllByStudentIdAndCreatedAtBetween(studentId, startOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC), lastOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC)));
  }

  public Collection<VacationDTO> query(LocalDate startOfMonth,  LocalDate lastOfMonth) {
    return mapper.toDTOs(getRepository().findAllByCreatedAtBetween(startOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC), lastOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC)));
  }

  public Map<String, Object> findChildren(Actor actor, AttendPeriodStat.PeriodType type, LocalDate specialDate) {
    Map<String, Object> map = new HashMap<>();
    Pair<LocalDate, LocalDate> pair = type.toPeriodRange(specialDate);
    Instant first = pair.getFirst().atStartOfDay().toInstant(ZoneOffset.UTC);
    Instant second = pair.getSecond().atStartOfDay().toInstant(ZoneOffset.UTC);

    User user = userRepository.findOne(actor.getUserId());

    List<Guardian> guardians = (List<Guardian>) guardianRepository.findByPatriarchId(user.getId());

    if (guardians.size() != 0){
      for (int i = 0; i < guardians.size(); i++) {
        Integer studentId = guardians.get(0).getStudent().getId();
        Collection<VacationDTO> vacations = mapper.toDTOs(getRepository().findAllByStudentIdAndCreatedAtBetween(studentId, first, second));
        map.put("vacations" + i, vacations);
      }
    }
    return map;
  }
}
