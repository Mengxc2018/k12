package cn.k12soft.servo.module.activiti.processNode.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.NodeDTO;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.NodeQueryDTO;
import cn.k12soft.servo.module.activiti.processNode.domain.form.NodeForm;
import cn.k12soft.servo.module.activiti.processNode.serivce.NodeService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/processNode/management")
public class NodeManagement {

    private final NodeService nodeService;

    @Autowired
    public NodeManagement(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * 新建流程
     * 保存到流程节点表中
     * 保存流程文件
     * ,将actorId以“1,2,3,4,5”的格式传进后台，逗号为英文逗号,不得有空格.
     * @param form
     * @return
     */
    @ApiOperation("新建流程")
    @PostMapping
    public NodeDTO create(@Active Actor actor,
                          @RequestBody @Valid NodeForm form){
        return nodeService.create(actor, form);
    }

    /**
     * 修改流程，传进来的审批人必须是个完整的流程审批人，不能只传要修改的人
     * @param form
     * @param nodeId
     * @return
     */
    @ApiOperation("修改流程审批人,传进来的审批人必须是个完整的流程审批人，不能只传要修改的人")
    @PutMapping
    public NodeDTO update(@Active Actor actor,
                          @RequestParam Integer nodeId,
                          @RequestBody @Valid NodeForm form){
        return nodeService.update(form, nodeId);
    }

    /**
     * 查询流程
     * @param actor
     * @return
     */
    @ApiOperation("查询流程审批人")
    @GetMapping
    public List<NodeQueryDTO> query(@Active Actor actor){
        return nodeService.query(actor.getSchoolId());
    }

    // 删除流程，同时ACT表中也要删除
    @ApiOperation("按照id删除")
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable("id") Integer id){
        nodeService.deleteProcess(new Long(id));
    }

    @ApiOperation("查询流程，按照不同流程类型返回需要用到的流程")
    @GetMapping("/queryByActorAndType")
    public NodeQueryDTO queryByActorAndType(@Active Actor actor,
                                            @RequestParam @Valid String type){
        return nodeService.queryByActorAndType(actor, type);
    }
}
