package cn.k12soft.servo.module.activiti.apply.vacationService;

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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Transactional
public class DynamicActivitiProcessTest {

    public final TaskService taskService;
    public final RuntimeService runtimeService;
    public RepositoryService repositoryService;

    @Autowired
    public DynamicActivitiProcessTest(TaskService taskService,
                                      RuntimeService runtimeService,
                                      RepositoryService repositoryService
    ) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
    }

    /**
     * 增加节点
     * 参数：
     *  要曾加节点的数量
     */
    public void createDynamicDeploy(){
    // 1. Build up the model from scratch 从头开始构建模型
    BpmnModel model = new BpmnModel();
    Process process = new Process();
    model.addProcess(process);
    process.setId("my-processNode");

    process.addFlowElement(createStartEvent());
    process.addFlowElement(createUserTask("task1", "First task", "fred"));
    process.addFlowElement(createUserTask("task2", "Second task", "john"));
    process.addFlowElement(createEndEvent());
    
    process.addFlowElement(createSequenceFlow("start", "task1"));
    process.addFlowElement(createSequenceFlow("task1", "task2"));
    process.addFlowElement(createSequenceFlow("task2", "end"));

    // 2. Generate graphical information 生成图形信息
    new BpmnAutoLayout(model).execute();

    // 3. Deploy the processNode to the engine 将过程部署到引擎中
    // 创建流程引擎
    ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
    // 得到流程存储服务组件
    //    repositoryService = engine.getRepositoryService();
    // 部署流程文件
    Deployment deployment = repositoryService.createDeployment()
              .addBpmnModel("dynamic-model.bpmn", model).name("Dynamic processNode deployment").deploy();

    // 查找流程定义
    ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();

    // 4. Start a processNode instance 启动一个流程实例
    ProcessInstance processInstance = runtimeService.startProcessInstanceById(pd.getId());

    // 5. Check if task is available 检查任务是否可用
    List<Task> tasks = taskService.createTaskQuery()
            .processInstanceId(processInstance.getId()).list();

    Assert.assertEquals(1, tasks.size());
    Assert.assertEquals("First task", tasks.get(0).getName());
    Assert.assertEquals("fred", tasks.get(0).getAssignee());
    
    try {
      // 6. Save processNode diagram to a file  将流程图保存到一个文件中
      InputStream processDiagram = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
      FileUtils.copyInputStreamToFile(processDiagram, new File("k12servo/src/main/resources/processes/diagram.png"));
        System.out.println(FileUtils.getTempDirectoryPath());


      // 7. Save resulting BPMN xml to a file 将产生的BPMN xml保存到一个文件中
        InputStream processBpmn = repositoryService.getResourceAsStream(deployment.getId(), "dynamic-model.bpmn");
        FileUtils.copyInputStreamToFile(processBpmn, new File("k12servo/src/main/resources/processes/processNode.bpmn"));
        System.out.println(FileUtils.getTempDirectoryPath());

    } catch (IOException e) {
      e.printStackTrace();
    }

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