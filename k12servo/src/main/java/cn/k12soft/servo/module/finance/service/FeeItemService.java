package cn.k12soft.servo.module.finance.service;

import cn.k12soft.servo.module.finance.repository.FeeItemRepository;
import cn.k12soft.servo.module.finance.rest.form.FeeItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeItemService {

  private final FeeItemRepository repository;

  @Autowired
  public FeeItemService(FeeItemRepository repository) {this.repository = repository;}

  /**
   * 创建收费项目
   *
   * @param schoolId 学校ID
   * @param form 表单
   * @param operatorId 操作人
   */
  public void create(Integer schoolId, FeeItemForm form, Integer operatorId) {
  }
}
