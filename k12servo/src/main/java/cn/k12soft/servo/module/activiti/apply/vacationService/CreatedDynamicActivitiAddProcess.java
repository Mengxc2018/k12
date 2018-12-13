package cn.k12soft.servo.module.activiti.apply.vacationService;

import cn.k12soft.servo.module.activiti.processNode.domain.form.NodeActorIds;
import cn.k12soft.servo.module.activiti.processNode.domain.form.NodeForm;
import cn.k12soft.servo.module.activiti.processNode.repository.NodeRepository;
import junit.framework.Assert;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CreatedDynamicActivitiAddProcess {

    public final TaskService taskService;
    public final RuntimeService runtimeService;
    public RepositoryService repositoryService;
    private final NodeRepository nodeRepository;
    
    @Autowired
    public CreatedDynamicActivitiAddProcess(TaskService taskService,
                                            RuntimeService runtimeService,
                                            RepositoryService repositoryService,
                                            NodeRepository nodeRepository) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.nodeRepository = nodeRepository;
    }

    /**
     * 添加请假流程
     * 参数：
     *  要曾加节点的数量，节点的数量=审批人的数量+开始结束两个节点+申请人的节点
     *  只传审批人的actorIds
     */
    public String createDynamicDeploy(NodeForm form, String processKey){

        // 获得审批者数量
        int size = form.getActor().size();
        List<NodeActorIds> actorIdxs = form.getActor();

        // 1. Build up the model from scratch 从头开始构建模型
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId(processKey);
        process.setName(processKey);

        process.addFlowElement(createStartEvent());
        // 申请者节点
        process.addFlowElement(createUserTask("task1", "applyForTask", "applyForUser"));
        // 审批人节点
        for (int i = 0; i < size; i ++ ) {
            String actorId = actorIdxs.get(i).getActorId().toString();
            String task = "task" + (2 + i);
            process.addFlowElement(createUserTask(task, task, actorId));
        }
        process.addFlowElement(createEndEvent());

        process.addFlowElement(createSequenceFlow("start", "task1"));
        int i = 0;
        String task1 = "task";
        String task2 = "task";
        for (i = 0; i < size; i ++ ) {
            task1 = "task" + (1 + i);
            task2 = "task" + (2 + i);
            process.addFlowElement(createSequenceFlow(task1, task2));
        }
        process.addFlowElement(createSequenceFlow(task2, "end"));

        // 2. Generate graphical information 生成图形信息
        new BpmnAutoLayout(model).execute();

        // 3. Deploy the processNode to the engine 将过程部署到引擎中
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务组件
        //    repositoryService = engine.getRepositoryService();

        // 部署流程文件
        Deployment deployment = repositoryService.createDeployment().addBpmnModel(processKey + ".bpmn", model).name("Dynamic " + processKey + " deployment").deploy();

        // 查找流程定义
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        // 4. Start a processNode instance 启动一个流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(pd.getId());

        // 5. Check if task is available 检查任务是否可用
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId()).list();

        Assert.assertEquals(1, tasks.size());
        for (Task task : tasks){
            Assert.assertEquals("applyForTask", task.getName());
            Assert.assertEquals("applyForUser", task.getAssignee());
        }

        try {
          // 6. Save processNode diagram to a file  将流程图保存到一个文件中
          InputStream processDiagram = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
          FileUtils.copyInputStreamToFile(processDiagram, new File("k12servo/src/main/resources/processes/" + processKey + ".png"));


          // 7. Save resulting BPMN xml to a file 将产生的BPMN xml保存到一个文件中
            InputStream processBpmn = repositoryService.getResourceAsStream(deployment.getId(), processKey + ".bpmn");
            FileUtils.copyInputStreamToFile(processBpmn, new File("k12servo/src/main/resources/processes/" + processKey + ".bpmn"));

        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("流程已创建---");
        return deployment.getId();

  }
  
  protected UserTask createUserTask(String id, String name, String assignee) {
    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    userTask.setAssignee(assignee);
    return userTask;
  }
  
  protected SequenceFlow createSequenceFlow(String from, String to) {
    SequenceFlow flow = new SequenceFlow();
    flow.setSourceRef(from);
    flow.setTargetRef(to);
    return flow;
  }
  
  protected StartEvent createStartEvent() {
    StartEvent startEvent = new StartEvent();
    startEvent.setId("start");
    return startEvent;
  }
  
  protected EndEvent createEndEvent() {
    EndEvent endEvent = new EndEvent();
    endEvent.setId("end");
    return endEvent;
  }
}