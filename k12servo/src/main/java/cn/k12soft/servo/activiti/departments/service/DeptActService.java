package cn.k12soft.servo.activiti.departments.service;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 部门同步到activiti的group组管理
 */
@Component
@Transactional
public class DeptActService {

    public String createdGroup(String name){
        ProcessEngine processEngines = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngines.getIdentityService();
        String uuid = UUID.randomUUID().toString().replace("-","");
        Group group = identityService.newGroup(uuid);
        group.setName(name);
        identityService.saveGroup(group);
        return uuid;
    }

    public void updateGroup(String groupId, String name){
        ProcessEngine processEngines = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngines.getIdentityService();
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        group.setName(name);
        identityService.saveGroup(group);
    }

    public void deleteGroup(String groupId){
        ProcessEngine processEngines = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngines.getIdentityService();
        identityService.deleteGroup(groupId);
    }

}
