package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.IKStudent;
import cn.k12soft.servo.repository.IKStudentRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IKStudentService extends AbstractEntityService<IKStudent, Integer> {
    public IKStudentService(IKStudentRepository repository) {
        super(repository);
    }


    public IKStudent findByIklassIdAndStudentId(Integer iklassId, Integer studentId) {
        return getEntityRepository().findByIklassIdAndStudentId(iklassId, studentId);
    }

    @Override
    protected IKStudentRepository getEntityRepository() {
        return (IKStudentRepository) super.getEntityRepository();
    }

    public void deleteByIklassId(Integer iKlassId) {
        getEntityRepository().deleteByIklassId(iKlassId);
    }

    public List<IKStudent> findByIklassId(Integer klassId) {
        return getEntityRepository().findByIklassId(klassId);
    }
}
