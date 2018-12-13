package cn.k12soft.servo.module.activiti.processNode.serivce;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.activiti.processNode.domain.Node;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.MassageCC;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.NodeDTO;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.NodeQueryDTO;
import cn.k12soft.servo.module.activiti.processNode.domain.dto.NodeQueryListDTO;
import cn.k12soft.servo.module.activiti.processNode.domain.form.NodeActorIds;
import cn.k12soft.servo.module.activiti.processNode.domain.form.NodeForm;
import cn.k12soft.servo.module.activiti.processNode.repository.NodeRepository;
import cn.k12soft.servo.module.activiti.processNode.serivce.mapper.NodeMapper;
import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.repositpry.DutyRepository;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.module.activiti.apply.vacationService.*;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class NodeService extends AbstractRepositoryService<Node, Long, NodeRepository> {

    private final NodeMapper nodeMapper;
    private final RuntimeService runtimeService;
    private final DutyRepository dutyRepository;
    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final RepositoryService repositoryService;
    private final EmployeeRepository employeeRepository;
    private final CreatedDynamicActivitiAddProcess createdProcess;

    @Autowired
    protected NodeService(NodeRepository repository,
                          NodeMapper nodeMapper,
                          RuntimeService runtimeService,
                          DutyRepository dutyRepository,
                          DutyRepository dutyRepository1, UserRepository userRepository,
                          ActorRepository actorRepository,
                          RepositoryService repositoryService,
                          EmployeeRepository employeeRepository,
                          CreatedDynamicActivitiAddProcess createdDynamicActivitiAddProcess) {
        super(repository);
        this.nodeMapper = nodeMapper;
        this.runtimeService = runtimeService;
        this.dutyRepository = dutyRepository1;
        this.userRepository = userRepository;
        this.repositoryService = repositoryService;
        this.employeeRepository = employeeRepository;
        this.actorRepository = actorRepository;
        this.createdProcess = createdDynamicActivitiAddProcess;
    }

    public NodeDTO create(Actor actorx, NodeForm form) {

        // 流程人数效验
        int size = form.getActor().size();
        if (size >= 8){
            throw new IllegalArgumentException("审批人数超过上限，最多8人");
        }

        // 员工是否分配效验
        for (NodeActorIds nodeActorIds : form.getActor()){
            Integer actorId = nodeActorIds.getActorId();
            Actor actor = actorRepository.getOne(Integer.valueOf(actorId));
            User user = userRepository.getOne(actor.getUserId());
            Employee employee = employeeRepository.findByActorId(actor.getId());
            if(employee == null){
                throw new IllegalArgumentException("该员工[ actorId:"+ actorId+" userName:" + user.getUsername()+ "] 未分配职务");
            }
        }

        // 基层职务效验
        Integer discernDutyId = form.getDiscernDutyId();
        Node byDiscernDuty = getRepository().findByDiscernDutyId(discernDutyId);
        if (byDiscernDuty != null){
            throw new IllegalArgumentException("当前职务 [ " + form.getProcessName() + " ] 流程已添加，请勿重新添加");
        }

        String processName = form.getProcessName();
        StringBuilder actorIds = new StringBuilder("");
        StringBuilder dutyIds = new StringBuilder("");
        StringBuilder num = new StringBuilder("");

        for (NodeActorIds actor : form.getActor()){
            actorIds.append(actor.getActorId());
            dutyIds.append(actor.getDutyId());
            num.append(actor.getNum());
            actorIds.append(",");
            dutyIds.append(",");
            num.append(",");
        }
        actorIds.replace(actorIds.length()-1, actorIds.length(), "");
        dutyIds.replace(dutyIds.length()-1, dutyIds.length(), "");
        num.replace(num.length()-1, num.length(), "");

        // 流程键的组成：vacationProcess + deploymentId
        String vacationProcessId = "vacationProcess" + UUID.randomUUID().toString().replace("-", "");

        Node node = new Node(
                processName,
                actorIds.toString(),
                dutyIds.toString(),
                num.toString(),
                vacationProcessId,
                actorx.getSchoolId(),
                form.getActivitiType(),
                form.getDiscernDutyId(),
                actorx.getId(),
                Instant.now(),
                form.getMassageCC()
        );

        NodeDTO nodeDTO = nodeMapper.toDTO(getRepository().save(node));

        // 新建一个流程，将审批者id跟流程类型传进来
        String deploymentId = createdProcess.createDynamicDeploy(form, vacationProcessId);
        node.setDeploymentId(Integer.valueOf(deploymentId));
        getRepository().save(node);

        return nodeDTO;
    }

    public NodeDTO update(NodeForm form, Integer nodeId) {

        int size = form.getActor().size();
        if (size >= 8){
            throw new IllegalArgumentException("审批人数超过上限，最多8人");
        }

        // 员工是否分配效验
        for (NodeActorIds nodeActorIds : form.getActor()){
            Integer actorId = nodeActorIds.getActorId();
            Actor actor = actorRepository.getOne(Integer.valueOf(actorId));
            User user = userRepository.getOne(actor.getUserId());
            Employee employee = employeeRepository.findByActorId(actor.getId());
            if(employee == null){
                throw new IllegalArgumentException("该员工[ actorId:"+ actorId+" userName:" + user.getUsername()+ "] 未分配职务");
            }
        }

        Node node = getRepository().findOne(new Long(nodeId));

        StringBuilder actorIds = new StringBuilder("");
        StringBuilder dutyIds = new StringBuilder("");
        StringBuilder num = new StringBuilder("");

        for (NodeActorIds actor : form.getActor()){
            actorIds.append(actor.getActorId());
            dutyIds.append(actor.getDutyId());
            num.append(actor.getNum());
            actorIds.append(",");
            dutyIds.append(",");
            num.append(",");
        }
        actorIds.replace(actorIds.length()-1, actorIds.length(), "");
        dutyIds.replace(dutyIds.length()-1, dutyIds.length(), "");
        num.replace(num.length()-1, num.length(), "");

        node.setProcessName(form.getProcessName());
        node.setActorIds(actorIds.toString());
        node.setDutyIds(dutyIds.toString());
        node.setNum(num.toString());

        NodeDTO nodeDTO = nodeMapper.toDTO(node);
        String vacationProcessId = node.getPROCESSKEY();
        // 修改activiti工作流中的流程
        createdProcess.createDynamicDeploy(form, vacationProcessId);

        return nodeDTO;
    }

    public List<NodeQueryDTO> query(Integer schoolId) {
        Integer processType = 1;
        Collection<NodeDTO> nodeDTOS = nodeMapper.toDTOs(getRepository().findBySchoolIdAndProcessType(schoolId, processType));
        if (nodeDTOS == null){
            throw new IllegalArgumentException("没有数据");
        }

        List<NodeQueryDTO> queryDTOList = new ArrayList<>();

        for (NodeDTO nodeDTO : nodeDTOS){

            String[] actorIds = nodeDTO.getActorIds().split(",");

            List<NodeQueryListDTO> nodeQueryListDTOS = new ArrayList<>();
            for (int i = 0; i < actorIds.length; i++){
                Actor actor = actorRepository.getOne(Integer.valueOf(actorIds[i]));
                User user = userRepository.getOne(actor.getUserId());
                Employee employee = employeeRepository.findByActorId(actor.getId());

                NodeQueryListDTO nodeQueryListDTO = new NodeQueryListDTO(
                        actor.getId(),
                        user.getUsername(),
                        user.getPortrait(),
                        Integer.valueOf(employee.getDuty().getId().toString()),
                        employee.getDuty().getName()
                );
                nodeQueryListDTOS.add(nodeQueryListDTO);
            }

            // 抄送人，actorId，姓名----START-------------
            List<MassageCC> messageCcList = new ArrayList();
            String[] messageCCId = nodeDTO.getMassageCC().split(",");
            for (String mcId : messageCCId){
                // 查询用户名
                Actor actor = actorRepository.findOne(Integer.valueOf(mcId));
                User user = userRepository.findOne(actor.getUserId());
                MassageCC massageCC = new MassageCC(mcId, user.getUsername(), user.getPortrait());
                messageCcList.add(massageCC);
            }
            // ----------------------------END---------------

            Duty duty = dutyRepository.findOne(Long.parseLong(nodeDTO.getDiscernDutyId().toString()));
            if (duty == null){
                duty = new Duty();
            }
            NodeQueryDTO nodeQueryDTO = new NodeQueryDTO(
                    nodeDTO.getId(),
                    nodeQueryListDTOS,
                    nodeDTO.getSchoolId(),
                    nodeDTO.getProcessType(),
                    messageCcList,
                    duty
            );
            queryDTOList.add(nodeQueryDTO);
        }
        return queryDTOList;
    }

    public void deleteProcess(Long aLong) {

        Node node = getRepository().getOne(aLong);
        String deploymentId = node.getDeploymentId().toString();
//        String resourceName = repositoryService.getProcessDefinition(deploymentId).getResourceName();   // bpmn的文件名
        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
        String bpmn = "k12servo/src/main/resources/processes/" + deploymentResourceNames.get(0);

        String name = bpmn.substring(0, bpmn.indexOf(".")); // 除去后缀的文件名
        String png = name + ".png";
        try{
            //普通删除，如果当前部署的规则还存在正在操作的流程，则抛异常
            repositoryService.deleteDeployment(node.getDeploymentId().toString(), true);
        }catch (Exception e){
            System.out.println("该流程正在使用，如果确实要删除，请审批完成之后再删除");
        }

        // 删除对应的文件
        File fileBpmn = new File(bpmn);
        File filePng = new File(png);
        if (fileBpmn.delete() && filePng.delete()){
            System.out.println("删除成功！");
        }else{
            System.out.println("文件删除失败");
        }

        getRepository().delete(aLong);

    }

    public NodeQueryDTO queryByActorAndType(Actor actor, String type) {

        Integer actorId = actor.getId();

        //获取对应的流程， 通过该员工职务，找到使用哪个流程，获取请假流程的key
        Employee employee = employeeRepository.findByActorId(actorId);

        // 找到基层职位的流程
        Node node = getRepository().findByDiscernDutyId(Integer.valueOf(employee.getDuty().getId().toString()));

        List<NodeQueryListDTO> actorList = new ArrayList<>();
        List<MassageCC> massageCCS = new ArrayList<>();

        // 如果基层职位为空，判断是否为非基础职位，如果还为空，则报错误
        Node nodex = new Node();
        if(node == null){
            List<Node> nodes = getRepository().findByProcessType(Integer.valueOf(type));
            String actorIda = String.valueOf(actorId);
            for (Node nodea : nodes) {
                String[] nodeByActorIds = nodea.getActorIds().split(",");
                for (String actorIdx : nodeByActorIds) {
                    if (actorIda.equals(actorIdx)) {
                        node = nodea;
                        break;
                    }
                }
            }

        }

        // 审批人组装
        String[] actorIds = node.getActorIds().split(",");
        for (String n : actorIds) {
            Actor actorx = actorRepository.findOne(Integer.valueOf(n));
            User user = userRepository.findOne(actorx.getUserId());
            Employee employee1 = employeeRepository.findByActorId(actorx.getId());
            NodeQueryListDTO nodeQueryListDTO = new NodeQueryListDTO(
                    Integer.valueOf(n),
                    user.getUsername(),
                    user.getPortrait(),
                    Integer.valueOf(employee.getDuty().getId().toString()),
                    employee1.getDuty().getName()
            );
            actorList.add(nodeQueryListDTO);
        }
        // 抄送人组装
        String[] massageCCs = node.getMassageCC().split(",");
        for (String m : massageCCs) {
            Actor actorx = actorRepository.findOne(Integer.valueOf(m));
            User user = userRepository.findOne(actorx.getUserId());
            MassageCC massageCC = new MassageCC(
                    String.valueOf(actor.getId()),
                    user.getUsername(),
                    user.getPortrait()
            );
            massageCCS.add(massageCC);
        }

        if (nodex == null){
            throw new IllegalArgumentException("没有可用流程");
        }

        NodeQueryDTO nodeQueryDTO = new NodeQueryDTO(
                node.getId(),
                actorList,
                actor.getSchoolId(),
                Integer.valueOf(type),
                massageCCS,
                employee.getDuty()
        );

        return nodeQueryDTO;
    }
}
