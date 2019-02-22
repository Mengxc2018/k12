package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherSocialSecurityService extends AbstractEntityService<TeacherSocialSecurity, Integer> {

    @Autowired
    public TeacherSocialSecurityService(TeacherSocialSecurityRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected TeacherSocialSecurityRepository getEntityRepository(){
        return (TeacherSocialSecurityRepository)super.getEntityRepository();
    }

    public Page<TeacherSocialSecurity> findBySchoolId(Integer schoolId, Pageable pageable) {
        return getEntityRepository().findBySchoolId(schoolId, pageable);
    }

    public Page<TeacherSocialSecurity> findByNameAndSchoolId(String name, Integer schoolId, Pageable pageable) {
        return getEntityRepository().findByNameAndSchoolId(name, schoolId, pageable);
    }

    public List<TeacherSocialSecurity> findAllBySchoolId(Integer schoolId){
        return getEntityRepository().findBySchoolId(schoolId);
    }
}
