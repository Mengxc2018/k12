package cn.k12soft.servo.module.weixin.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.weixin.admin.WeixinPayRecord;
import cn.k12soft.servo.module.weixin.admin.WxPayRecordDTO;
import cn.k12soft.servo.module.weixin.repository.WeixinPayRecordRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;

@Service
@Transactional
public class WeixinPayRecodeService extends AbstractRepositoryService<WeixinPayRecord, Long, WeixinPayRecordRepository> {

    @Autowired
    private WxPayRecordMapper wxPayRecordMapper;

    protected WeixinPayRecodeService(WeixinPayRecordRepository repository) {
        super(repository);
    }


    public Collection<WxPayRecordDTO> findOrder(Actor actor, LocalDate localDate) {
        Instant first = localDate.atStartOfDay().with(TemporalAdjusters.firstDayOfMonth()).toInstant(ZoneOffset.UTC);
        Instant second = localDate.plusDays(1).atStartOfDay().with(TemporalAdjusters.firstDayOfMonth()).toInstant(ZoneOffset.UTC);
        return wxPayRecordMapper.toDTOs(this.getRepository().findAllByCreateAtBetween(first, second));
    }
}

