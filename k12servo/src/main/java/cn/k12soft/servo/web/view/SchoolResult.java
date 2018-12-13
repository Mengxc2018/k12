package cn.k12soft.servo.web.view;

import cn.k12soft.servo.service.dto.ManagerDTO;
import cn.k12soft.servo.service.dto.PatriarchDTO;
import cn.k12soft.servo.service.dto.RegionUserDTO;
import cn.k12soft.servo.service.dto.TeacherDTO;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
public class SchoolResult {

  private String token;
  private PatriarchDTO patriarch;
  private TeacherDTO teacher;
  private ManagerDTO manager;
  private RegionUserDTO regionUser;


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public PatriarchDTO getPatriarch() {
    return patriarch;
  }

  public void setPatriarch(PatriarchDTO patriarch) {
    this.patriarch = patriarch;
  }

  public TeacherDTO getTeacher() {
    return teacher;
  }

  public void setTeacher(TeacherDTO teacher) {
    this.teacher = teacher;
  }

  public ManagerDTO getManager() {
    return manager;
  }

  public void setManager(ManagerDTO manager) {
    this.manager = manager;
  }

  public RegionUserDTO getRegionUser() {
    return regionUser;
  }

  public void setRegionUser(RegionUserDTO regionUser) {
    this.regionUser = regionUser;
  }
}
