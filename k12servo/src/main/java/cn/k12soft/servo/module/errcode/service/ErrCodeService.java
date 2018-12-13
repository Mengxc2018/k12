package cn.k12soft.servo.module.errcode.service;

import cn.k12soft.servo.module.errcode.domain.ErrCode;
import cn.k12soft.servo.module.errcode.domain.ErrCodeForm;
import cn.k12soft.servo.module.errcode.resource.ErrCodeResource;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ErrCodeService extends AbstractRepositoryService<ErrCode, Long, ErrCodeResource> {
    protected ErrCodeService(ErrCodeResource repository) {
        super(repository);
    }

    public ResponseEntity<List<ErrCode>> findBy() {
        return ResponseEntity.ok().body(this.getRepository().findAll(new Sort(Sort.Direction.ASC, "errcode")));
    }

    public ResponseEntity<List<ErrCode>> create(List<ErrCodeForm> forms) {
        Map<String, Object> map = new HashMap<>();
        for (ErrCodeForm form : forms){
            Optional<ErrCode> errCode = this.getRepository().findByErrcode(form.getErrcode());
            if (!errCode.isPresent()){
                ErrCode errCoded = new ErrCode(
                        form.getErrcode(),
                        form.getErrmsg()
                );
                this.getRepository().save(errCoded);
            }else {
                return new ResponseEntity("错误码：" + form.getErrcode() + "已存在", HttpStatus.BAD_REQUEST);
            }
        }
        return this.findBy();
    }
}
