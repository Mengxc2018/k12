package cn.k12soft.servo.activiti.departments.web;

import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/wxLogin/web")
@RestController
public class DeptActTest {

    @ApiOperation("获取组")
    @GetMapping("/findGroup")
    public List<Group> findGroup(){
        ProcessEngine processEngines = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngines.getIdentityService();
        List<Group> list = identityService.createGroupQuery().list();
        return list;
    }

    @ApiOperation("注册、注销")
    @GetMapping("/register")
    public Map<String, Integer> registerProcessEngine(){
        ProcessEngineConfiguration cfg =
//                ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

        ProcessEngine processEngine = cfg.buildProcessEngine();

        Map<String, Integer> map = new LinkedHashMap<>();
        // 查询注册后的
        Map<String, ProcessEngine> engines = ProcessEngines.getProcessEngines();
        map.put("注册前的引擎数：", engines.size());

        // 查询注销后的
        ProcessEngines.unregister(processEngine);
        processEngine.close();
        map.put("注册后的引擎数：", engines.size());

        return map;
    }

}
