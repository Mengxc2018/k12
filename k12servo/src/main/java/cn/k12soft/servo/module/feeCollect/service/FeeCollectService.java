package cn.k12soft.servo.module.feeCollect.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.domain.enumeration.FeeType;
import cn.k12soft.servo.module.feeCollect.domain.FeeCollect;
import cn.k12soft.servo.module.feeCollect.domain.dto.FeeCollectDTO;
import cn.k12soft.servo.module.feeCollect.repository.FeeCollectRepository;
import cn.k12soft.servo.module.feeCollect.service.mapper.FeeCollectMapper;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

@Service
@Transactional
public class FeeCollectService extends AbstractRepositoryService<FeeCollect, Long, FeeCollectRepository>{

    private final FeeCollectMapper feeCollectMapper;

    protected FeeCollectService(FeeCollectRepository repository, FeeCollectMapper feeCollectMapper) {
        super(repository);
        this.feeCollectMapper = feeCollectMapper;
    }

    public void create(String name, Float money, FeeKindType scope, FeeType feeType, Integer msgId){
        FeeCollect feeCollect = new FeeCollect(
                name,
                money,
                scope,
                feeType,
                msgId,
                Instant.now()
        );
        this.getRepository().save(feeCollect);
    }

    public Collection<FeeCollectDTO> findByMonth(Actor actor, LocalDate localDate) {
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        return feeCollectMapper.toDTOs(this.getRepository().findAllByCreatedAtBetween(first, second));
    }
}
