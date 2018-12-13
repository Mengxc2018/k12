package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.News;
import cn.k12soft.servo.domain.NewsEvent;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.enumeration.CURDType;
import cn.k12soft.servo.domain.enumeration.PlanEventType;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.repository.NewsRepository;
import cn.k12soft.servo.service.dto.NewsListDTO;
import cn.k12soft.servo.web.form.NewsForm;
import java.time.Instant;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 新闻
 */
@Service
@Transactional
public class NewsService extends AbstractEntityService<News, Integer> {

  private final SchoolService schoolService;
  private final NewsEventService newsEventService;

  @Autowired
  protected NewsService(NewsRepository repository, SchoolService schoolService, NewsEventService newsEventService) {
    super(repository);
    this.schoolService = schoolService;
    this.newsEventService = newsEventService;
  }

  @Transactional(readOnly = true)
  public List<News> findAllBySchool(Integer schoolId) {
    return getEntityRepository().findAllBySchoolId(schoolId);
  }

  @Transactional(readOnly = true)
  public NewsListDTO findAllBySchoolAndFrom(Integer schoolId, long fromDate) {
    return newsEventService.aggregate(schoolId, fromDate);
  }

  @Transactional(readOnly = true)
  public News findById(int id) {
    return getEntityRepository().findOne(id);
  }

  public News create(Actor createdBy, NewsForm form) {
      School school = schoolService.get(form.getSchoolId());
      News news = new News(school, form.getTitle(), form.getContent(), createdBy);
      getEntityRepository().save(news);
      NewsEvent event = new NewsEvent(news, CURDType.CREATED, news.getId(), Instant.now());
      this.newsEventService.save(event);
      return news;
  }

  public News update(int id, NewsForm form) {
    News news = get(id);
    if (StringUtils.isNotBlank(form.getTitle())) {
      news.setTitle(form.getTitle());
    }
    if (StringUtils.isNotBlank(form.getContent())) {
      news.setContent(form.getContent());
    }
    NewsEvent event = new NewsEvent(news, CURDType.UPDATED, news.getId(), Instant.now());
    this.newsEventService.save(event);
    return getEntityRepository().save(news);
  }

  @Override
  public void delete(Integer id) {
      News news = get(id);
      if(news != null){
          NewsEvent event = new NewsEvent(news, CURDType.DELETED, news.getId(), Instant.now());
          this.newsEventService.save(event);
          getEntityRepository().delete(news);
      }
  }

  @Override
  protected NewsRepository getEntityRepository() {
    return (NewsRepository) super.getEntityRepository();
  }
}
