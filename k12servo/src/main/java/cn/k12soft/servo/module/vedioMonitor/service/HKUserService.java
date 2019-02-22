package cn.k12soft.servo.module.vedioMonitor.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUser;
import cn.k12soft.servo.module.vedioMonitor.repository.HKUserRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by liubing on 2018/3/31
 */
@Service
@Transactional
public class HKUserService extends AbstractEntityService<HKUser, Integer> {

    public HKUserService(JpaRepository<HKUser, Integer> entityRepository) {
        super(entityRepository);
    }

    public HKUserRepository getEntityRepository(){
        return (HKUserRepository)super.getEntityRepository();
    }

    public HKUser findByUserId(Integer userId) {
        return getEntityRepository().findByUserId(userId);
    }

    public Collection<HKUser> findByAll(Actor actor, Integer klassId) {
        Integer schoolId = actor.getSchoolId();
        return this.getEntityRepository().findAllBySchoolIdAndKlassId(schoolId, klassId, new Sort(Sort.Direction.ASC, "state"));
    }

    public HKUser findOneUser(Integer schoolId, Integer userId) {
        return this.getEntityRepository().findBySchoolIdAndUserId(schoolId, userId);
    }
}
