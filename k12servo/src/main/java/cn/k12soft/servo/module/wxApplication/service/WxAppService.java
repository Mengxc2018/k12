package cn.k12soft.servo.module.wxApplication.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.wxApplication.domain.WxAppForm;
import cn.k12soft.servo.module.wxApplication.domain.WxApplication;
import cn.k12soft.servo.module.wxApplication.repository.WxAppRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class WxAppService extends AbstractRepositoryService<WxApplication, Long, WxAppRepository>{
    protected WxAppService(WxAppRepository repository) {
        super(repository);
    }


    public WxApplication create(Actor actor, WxAppForm form) {

        Collection<WxApplication> wxApplications = getRepository().findAll();
        if (wxApplications.size() > 0){
            throw new IllegalArgumentException("只能创建一个，请确认后再操作");
        }
        WxApplication wxApplication = new WxApplication(
                form.getSecret(),
                form.getAppid(),
                form.getGrantType()
        );
        return getRepository().save(wxApplication);
    }

    public WxApplication update(Actor actor, Integer id, WxAppForm form) {

        Long idl = Long.parseLong(id.toString());
        WxApplication wxApplication = getRepository().findOne(idl);
        if (!Strings.isNullOrEmpty(form.getAppid())){
            wxApplication.setAppid(form.getAppid());
        }
        if (!Strings.isNullOrEmpty(form.getGrantType())){
            wxApplication.setGrantType(form.getGrantType());
        }
        if (!Strings.isNullOrEmpty(form.getSecret())){
            wxApplication.setSecret(form.getSecret());
        }
        return getRepository().save(wxApplication);
    }

    public List<WxApplication> findApp(Actor actor) {
        return getRepository().findAll();
    }

    public void deleteApp(Actor actor, Integer id) {
        getRepository().delete(Long.parseLong(id.toString()));
    }
}
