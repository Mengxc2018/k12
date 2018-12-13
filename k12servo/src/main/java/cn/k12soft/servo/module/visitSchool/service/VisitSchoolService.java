package cn.k12soft.servo.module.visitSchool.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.IsVisit;
import cn.k12soft.servo.domain.enumeration.IsWill;
import cn.k12soft.servo.module.visitSchool.domain.VisitSchool;
import cn.k12soft.servo.module.visitSchool.domain.form.VisitSchoolForm;
import cn.k12soft.servo.module.visitSchool.repository.VisitSchoolRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

@Service
public class VisitSchoolService extends AbstractRepositoryService<VisitSchool, Long, VisitSchoolRepository>{

    private static final IsWill UNWILL = IsWill.UNWILL;     // 不愿意入园
    private static final IsWill WILL = IsWill.WILL;       // 待定
    private static final IsWill WILLOK = IsWill.WILLOK;     // 愿意入园

    private static final IsVisit UNVISIT = IsVisit.UNVISIT; // 未访问
    private static final IsVisit VISITING = IsVisit.VISITING;// 正在访问
    private static final IsVisit VISITOK = IsVisit.VISITOK; // 访问结束

    @Autowired
    public VisitSchoolService(VisitSchoolRepository repository) {
        super(repository);
    }

    public VisitSchool create(VisitSchoolForm form) {
        VisitSchool visitSchool = new VisitSchool(
                form.getParentName(),
                form.getMobile(),
                form.getBabyName(),
                form.getBabyAge(),
                form.getSchoolId(),
                Instant.now(),
                UNVISIT,
                WILL
        );
        return getRepository().save(visitSchool);
    }

    public Collection<VisitSchool> findNoVisit(Actor actor, IsVisit isVisit, LocalDate specialDate) {
        Instant first = specialDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.ofHours(8));
        Instant second = specialDate.withDayOfMonth(specialDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.ofHours(8));
        Collection<VisitSchool> visitSchools = getRepository().findBySchoolIdAndIsVisitAndCreatedAtBetween(actor.getSchoolId(), isVisit, first, second, new Sort(Sort.Direction.DESC, "createdAt"));
        return visitSchools;
    }
}
