package cn.k12soft.servo.module.department.service;

import cn.k12soft.servo.activiti.departments.service.DeptActService;
import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.module.department.domain.Dept;
import cn.k12soft.servo.module.department.domain.form.DeptForm;
import cn.k12soft.servo.module.department.repository.DeptRepository;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.activiti.engine.ProcessEngines;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class DeptService extends AbstractRepositoryService<Dept, Long, DeptRepository>{

    private final SchoolRepository schoolRepository;
    private final CitysRepository citysRepository;
    private final ProvincesRepository provincesRepository;
    private final RegionsRepository regionsRepository;
    private final GroupsRepository groupsRepository;

    private final DeptActService deptActService;

    private ProcessEngines processEngines;

    protected DeptService(DeptRepository repository, SchoolRepository schoolRepository, CitysRepository citysRepository, ProvincesRepository provincesRepository, RegionsRepository regionsRepository, GroupsRepository groupsRepository, DeptActService deptActService) {
        super(repository);
        this.schoolRepository = schoolRepository;
        this.citysRepository = citysRepository;
        this.provincesRepository = provincesRepository;
        this.regionsRepository = regionsRepository;
        this.groupsRepository = groupsRepository;
        this.deptActService = deptActService;
    }

    private final Logger log = LoggerFactory.logger(DeptService.class);

    public void create(List<DeptForm> forms) {
        for (DeptForm form : forms){
            String actId = deptActService.createdGroup(form.getName());        // 同步到activiti流程组管理
            Dept dept = new Dept(form.getName(), actId, Instant.now());
            this.getRepository().save(dept);
        }
    }

    public void deleteBy(String ids) {
        String[] ida = ids.split(",");
        for (String id : ida){
            try {
                Dept dept = this.getRepository().findOne(Long.parseLong(id));
                deptActService.deleteGroup(dept.getActivitiId());
                this.getRepository().delete(dept);
            }catch (Exception e){
                log.error(e.toString());
            }
        }
    }

    public List<Dept> findBy(Actor actor) {
        ActorType types = actor.getTypes().iterator().next();
        List<Dept> list = new ArrayList<>();
        Iterator<Dept> deptIterator = null;
        Dept dept = new Dept();
        School school = null;
        Citys citys = null;
        Provinces provinces = null;
        Regions regions = null;
        Groups groups = null;

        switch(types){
            case MANAGER:
                school = schoolRepository.findOne(actor.getSchoolId());
                deptIterator = school.getDepartment().iterator();
                while (deptIterator.hasNext()){
                    dept = this.getRepository().findOne(Long.parseLong(deptIterator.next().getId().toString()));
                    list.add(dept);
                }
                break;
            case CITY:
                citys = citysRepository.findById(Integer.valueOf(actor.getCityId()));
                deptIterator = citys.getDepartment().iterator();
                while (deptIterator.hasNext()){
                    dept = this.getRepository().findOne(Long.parseLong(deptIterator.next().getId().toString()));
                    list.add(dept);
                }
                break;
            case PROVINCE:
                provinces = this.provincesRepository.findById(Integer.valueOf(actor.getProvinceId()));
                deptIterator = provinces.getDepartment().iterator();
                while (deptIterator.hasNext()){
                    dept = this.getRepository().findOne(Long.parseLong(deptIterator.next().getId().toString()));
                    list.add(dept);
                }
                break;
            case REGION:
                regions = this.regionsRepository.findById(Integer.valueOf(actor.getRegionId()));
                deptIterator = regions.getDepartment().iterator();
                while (deptIterator.hasNext()){
                    dept = this.getRepository().findOne(Long.parseLong(deptIterator.next().getId().toString()));
                    list.add(dept);
                }
                break;
            case GROUP:
                groups = this.groupsRepository.findById(Integer.valueOf(actor.getGroupId()));
                deptIterator = groups.getDepartment().iterator();
                while (deptIterator.hasNext()){
                    dept = this.getRepository().findOne(Long.parseLong(deptIterator.next().getId().toString()));
                    list.add(dept);
                }
                break;
            default:
                return null;
        }

        return list;
    }

    public List<Dept> findByAll(Actor actor) {
        ActorType types = actor.getTypes().iterator().next();
        List<Dept> list = new ArrayList<>();
        switch(types){
            case PATRIARCH:
                break;
            case TEACHER:
                break;
            default:
                list = this.getRepository().findAll();
        }
        return list;
    }

    public void updateBy(Integer id, DeptForm form) {
        Dept dept = this.getRepository().findOne(Long.parseLong(id.toString()));
        String actId = dept.getActivitiId();
        dept.setName(form.getName());
        deptActService.updateGroup(actId, form.getName());
        this.getRepository().save(dept);
    }
}
