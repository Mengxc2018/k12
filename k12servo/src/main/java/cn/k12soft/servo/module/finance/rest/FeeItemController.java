package cn.k12soft.servo.module.finance.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.finance.rest.form.FeeItemForm;
import cn.k12soft.servo.security.Active;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance/fee-item")
public class FeeItemController {

  @ApiOperation("创建收费项目")
  @PostMapping
  @Timed
  public void createItem(@Active Actor operator, FeeItemForm form) {
  }
}
