package cn.k12soft.servo.activiti.utils;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
//public class ActivitiUtils implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("ApplicationRunner");
//    }
//
//    public IdentityService activitiUtils(){
//        ProcessEngineConfiguration processEngines = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
//        return processEngines.getIdentityService();
//    }
//
//}
