package cn.k12soft.servo.module.activiti.processNode.serivce.mapper;

import cn.k12soft.servo.module.activiti.processNode.domain.Node;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.NodeDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class NodeMapper extends EntityMapper<Node, NodeDTO>{

    @Override
    protected NodeDTO convert(Node node) {
        return new NodeDTO(
                node.getId(),
                node.getProcessName(),
                node.getActorIds(),
                node.getDutyIds(),
                node.getPROCESSKEY(),
                node.getSchoolId(),
                node.getProcessType(),
                node.getMassageCC(),
                node.getDiscernDutyId()
        );
    }
}
