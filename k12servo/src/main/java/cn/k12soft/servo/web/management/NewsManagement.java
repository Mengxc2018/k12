package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.News;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.NewsService;
import cn.k12soft.servo.service.dto.NewsListDTO;
import cn.k12soft.servo.web.form.NewsForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/news")
public class NewsManagement {

  private final NewsService newsService;

  public NewsManagement(NewsService newsService) {
    this.newsService = newsService;
  }

  @ApiOperation("创建新闻")
  @PostMapping
  @PermissionRequired(Permission.NEWS_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public News create(@Active Actor actor, @RequestBody @Valid NewsForm form) {
    return newsService.create(actor, form);
  }

  @ApiOperation("获取新闻列表")
  @GetMapping(params = {"schoolId"})
  @PermissionRequired(Permission.NEWS_VIEW)
  @Timed
  public NewsListDTO getAll(@RequestParam("schoolId") Integer schollId,
                            @RequestParam(value = "fromDate",required = false) long fromDate) {
      return newsService.findAllBySchoolAndFrom(schollId, fromDate);
  }

  @ApiOperation("更新新闻")
  @PutMapping("/{newsId:\\d+}")
  @PermissionRequired(Permission.NEWS_PUT)
  @Timed
  public News update(@PathVariable Integer newsId, @RequestBody @Valid NewsForm form) {
    return newsService.update(newsId, form);
  }

  @ApiOperation("删除新闻")
  @DeleteMapping("/{newsId:\\d+}")
  @PermissionRequired(Permission.NEWS_DELETE)
  @Timed
  public ResponseEntity<Void> delete(@PathVariable Integer newsId) {
    newsService.delete(newsId);
    return ResponseEntity.ok().build();
  }

}
