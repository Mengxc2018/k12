package cn.k12soft.servo.module.feeCollect.service.mapper;

import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.module.feeCollect.domain.FeeCollect;
import cn.k12soft.servo.module.feeCollect.domain.dto.FeeCollectDTO;
import cn.k12soft.servo.module.feeDetails.domain.FeeDetails;
import cn.k12soft.servo.module.feeDetails.repository.FeeDetailsRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeeCollectMapper extends EntityMapper<FeeCollect, FeeCollectDTO>{
    private final FeeDetailsRepository feeDetailsRepository;

    @Autowired
    public FeeCollectMapper(FeeDetailsRepository feeDetailsRepository) {
        this.feeDetailsRepository = feeDetailsRepository;
    }

    @Override
    protected FeeCollectDTO convert(FeeCollect feeCollect) {
        Integer msgId = feeCollect.getMsgId();
        FeeKindType type = feeCollect.getScopt();
        String str = "";
        switch (type){
            case VIDEOS:
                // 海康视频收费
                FeeDetails feeDetails = feeDetailsRepository.findOne(Long.parseLong(msgId.toString()));
                str = JSONObject.fromObject(feeDetails).toString();
                break;
        }
        return new FeeCollectDTO(
                feeCollect.getName(),
                feeCollect.getMoney(),
                feeCollect.getScopt(),
                feeCollect.getFeeType(),
                feeCollect.getMsgId(),
                feeCollect.getCreatedAt(),
                str
        );
    }
}
