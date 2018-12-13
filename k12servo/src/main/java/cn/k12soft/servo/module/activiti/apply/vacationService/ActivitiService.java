package cn.k12soft.servo.module.activiti.apply.vacationService;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.activiti.processNode.domain.Node;
import cn.k12soft.servo.module.activiti.processNode.repository.NodeRepository;
import cn.k12soft.servo.module.applyFor.domain.ApplyFor;
import cn.k12soft.servo.module.applyFor.repository.ApplyForRepository;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.third.aliyun.AliyunPushService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * 申请，批准程序为两层
 * manager审核
 * master审核
 */
@Service
@Transactional
public class ActivitiService {

    private final NodeRepository nodeRepository;
    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final AliyunPushService aliyunPushService;
    private final EmployeeRepository employeeRepository;
    private final ApplyForRepository applyForRepository;
    private final TaskService taskService;
    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    @Autowired
    public ActivitiService(
            NodeRepository nodeRepository,
            UserRepository userRepository,
            ActorRepository actorRepository,
            AliyunPushService aliyunPushService,
            EmployeeRepository employeeRepository,
            ApplyForRepository applyForRepository,
            TaskService taskService, HistoryService historyService,
            RuntimeService runtimeService, IdentityService identityService,
            RepositoryService repositoryService) {
        this.nodeRepository = nodeRepository;
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.aliyunPushService = aliyunPushService;
        this.employeeRepository = employeeRepository;
        this.applyForRepository = applyForRepository;
        this.taskService = taskService;
        this.historyService = historyService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
    }

    private static Logger log = LoggerFactory.getLogger(ActivitiService.class);

    /**
     * 申请：
     *  1、获取用户的actorId 跟上级的actorid
     *  2、请假状态跟时间赋值，并保存
     *  3、开始一个流程，并查询当前的任务，也就是第一个usertask
     *  4、申明一个任务，以map的形式保存信息
     *  5、通过complete提交保存流程
     *
     *  type:申请类型是请假
     */
    public void startProcess(Integer actorId, Integer massageId, String type, VacationTeacherUtil.VACATIONTYPE vacationType, String remark){

        log.info("开始申请...");

        // 获取actorID, 申请人的acotrId
        Actor actorByUser = actorRepository.findOne(actorId);
        User userByActorNext = userRepository.getOne(actorByUser.getUserId()); // 申请人信息
        boolean t = false;

        //获取对应的流程， 通过该员工职务，找到使用哪个流程，获取请假流程的key
        Employee employee = employeeRepository.findByActorId(actorId);

        // 找到基层职位的流程
        Node node = nodeRepository.findByDiscernDutyId(Integer.valueOf(employee.getDuty().getId().toString()));

        // 如果基层职位为空，判断是否为非基础职位，如果还为空，则报错误
        Node nodex = new Node();
        if(node == null){

            t = true;
            nodex = findByNode(actorId, type);

            if (nodex == null){
                throw new IllegalArgumentException("没有可用流程");
            }
            node = nodex;
        }

        // 开始流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(node.getPROCESSKEY());
        // 查询当前任务
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();

        // 提交任务
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("applyUser", actorByUser.getId());
        map.put("desc", remark);
        taskService.complete(task.getId(), map);

        // 查询下一级任务
        Task task2 = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        // 任务节点以提交的方式实现跳转，当当前的actorId与审批者的id相同时，再保存相关信息
        while(t){
            String strActorId = String.valueOf(actorId);
            if (strActorId.equals(task2.getAssignee())){
                t = false;
            }else {
                t = true;
                taskService.complete(task2.getId());
                task2 = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
            }
        }

        Actor actorNext = actorRepository.findOne(Integer.valueOf(task2.getAssignee()));    // 下一个审批人信息
        User userByMasterNext = userRepository.findOne(Integer.valueOf(actorNext.getUserId())); // 下一个审批人信息
        // 保存下一个审批者的审批请求
        // 业务跟 Task 会话保存后，保存申请信息到 applyFor
        ApplyFor applyFor = new ApplyFor(
                Integer.valueOf(task2.getAssignee()),
                userByMasterNext.getUsername(),
                userByMasterNext.getPortrait(),
                instance.getId(),
                node.getProcessType(),
                vacationType,
                VacationTeacherUtil.ISGONE.CHECKED,
                massageId,
                task2.getId(),
                actorByUser.getId(),
                userByActorNext.getUsername(),
                userByActorNext.getPortrait(),
                Instant.now()
        );
        applyForRepository.save(applyFor);

        // 推送消息
        aliyunPushService.sendTeaApplyNotification(userByMasterNext.getMobile());

        log.info("完成申请。。。。。。");
    }

    /**
     * 查询非基层中该申请者所在的流程
     * 找到后流程，给流程图动态添加流程线
     * 如果返回 null，则表示没有相应流程
     * @param actorId
     * @param type
     * @return
     */
    private Node findByNode(Integer actorId, String type) {
        List<Node> nodes = nodeRepository.findByProcessType(Integer.valueOf(type));
        String actorIda = String.valueOf(actorId);
        for (Node node : nodes){
            String[] nodeByActorIds = node.getActorIds().split(",");
            for (String actorIdx : nodeByActorIds){
                if (actorIda.equals(actorIdx)){

                    return node;
                }
            }
        }
        return null;
    }

    /**
     * 返回请假多少天
     * @param startTime
     * @param endTime
     * @return
     */
    public Long time(Instant startTime, Instant endTime){

        Long betweenTime = Duration.between(startTime, endTime).toDays();

        return betweenTime;
    }

}
