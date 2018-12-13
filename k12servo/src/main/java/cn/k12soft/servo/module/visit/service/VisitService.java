package cn.k12soft.servo.module.visit.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.IsVisit;
import cn.k12soft.servo.domain.enumeration.IsWill;
import cn.k12soft.servo.module.visit.domain.VisitParents;
import cn.k12soft.servo.module.visit.domain.form.VisitParentsForm;
import cn.k12soft.servo.module.visit.repository.VisitParentsRepository;
import cn.k12soft.servo.module.visitSchool.domain.VisitSchool;
import cn.k12soft.servo.module.visitSchool.repository.VisitSchoolRepository;
import cn.k12soft.servo.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class VisitService{

    private final VisitSchoolRepository visitSchoolRepository;
    private final UserRepository userRepository;
    public final VisitParentsRepository visitParentsRepository;

    protected VisitService(VisitSchoolRepository visitSchoolRepository, UserRepository userRepository, VisitParentsRepository visitParentsRepository) {
        this.visitSchoolRepository = visitSchoolRepository;
        this.userRepository = userRepository;
        this.visitParentsRepository = visitParentsRepository;
    }

    public VisitParents createdVisitParents(Actor actor, Integer visitSchoolId, VisitParentsForm form) {
        User user = userRepository.findOne(actor.getUserId());
        VisitSchool visitSchool = visitSchoolRepository.findOne(Long.parseLong(visitSchoolId.toString()));
        String mobile = visitSchool.getMobile();
        String parentsName = visitSchool.getParentName();
        VisitParents visitParents = new VisitParents(
                visitSchool,
                user.getUsername(),
                parentsName,
                mobile,
                form.getContent(),
                form.getIsWill(),
                Instant.now()
        );

        visitParents = visitParentsRepository.save(visitParents);
        visitSchool = visitSchoolRepository.findOne(Long.parseLong(visitSchoolId.toString()));

        visitSchool.setIsVisit(IsVisit.VISITING);

        if (visitParents.getIsWill().equals(IsWill.WILLOK)){
            visitSchool.setIsWill(IsWill.WILLOK);
            visitSchool.setIsVisit(IsVisit.VISITOK);
        }else if(visitParents.getIsWill().equals(IsWill.WILL)){
            visitSchool.setIsWill(IsWill.WILL);
            visitSchool.setIsVisit(IsVisit.VISITING);
        }
        visitSchoolRepository.save(visitSchool);

        return visitParents;
    }

    public Collection<VisitParents> findVisitParents(Actor actor, Integer visitSchoolId) {
        Long vsId = Long.parseLong(visitSchoolId.toString());
        return visitParentsRepository.findByVisitSchoolId(vsId, new Sort(Sort.Direction.DESC, "createdAt"));
    }
}
