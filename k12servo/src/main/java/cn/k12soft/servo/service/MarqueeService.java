package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Marquee;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.repository.MarqueeRepository;
import cn.k12soft.servo.web.form.MarqueeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by xfnjlove on 2017/12/26.
 */
@Service
@Transactional
public class MarqueeService extends AbstractEntityService<Marquee, Integer> {

    private final SchoolService schoolService;

    @Autowired
    public MarqueeService(MarqueeRepository marqueeRepository,
                           SchoolService schoolService) {
        super(marqueeRepository);
        this.schoolService = schoolService;
    }

    public Marquee create(Actor createdBy, MarqueeForm form) {
        School school = schoolService.get(form.getSchoolId());
        Marquee marquee = new Marquee(school, form.getFirstImg(), form.getCenterImg(), form.getLastImg());
        return getEntityRepository().save(marquee);
    }

    public Marquee update(Integer planId,
                           MarqueeForm form) {
        Marquee marquee = getEntityRepository().findOne(planId);
        if (!isNullOrEmpty(form.getFirstImg())) {
            marquee.setFirstImg(form.getFirstImg());

        }
        if (!isNullOrEmpty(form.getCenterImg())) {
            marquee.setCenterImg(form.getCenterImg());
        }
        if (!isNullOrEmpty(form.getLastImg())) {
            marquee.setLastImg(form.getLastImg());
        }
        return getEntityRepository().save(marquee);
    }

    @Transactional(readOnly = true)
    public Marquee getMarquee(Integer id) {
        return getEntityRepository().findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Marquee> findAllBySchool(Integer schoolId) {
        return getEntityRepository().findAllBySchoolId(schoolId);
    }

    public void delete(Integer planId) {
        getEntityRepository().delete(planId);
    }

    @Override
    protected MarqueeRepository getEntityRepository() {
        return (MarqueeRepository) super.getEntityRepository();
    }
}
