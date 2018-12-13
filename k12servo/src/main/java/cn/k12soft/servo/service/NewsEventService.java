package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.News;
import cn.k12soft.servo.domain.NewsEvent;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.repository.NewsEventRepository;
import cn.k12soft.servo.repository.NewsRepository;
import cn.k12soft.servo.service.dto.NewsListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class NewsEventService extends  AbstractEntityService<NewsEvent, Long>{
    private NewsRepository newsRepository;

    @Autowired
    public NewsEventService(NewsEventRepository newsEventRepository, NewsRepository newsRepository) {
        super(newsEventRepository);
        this.newsRepository = newsRepository;
    }

    @Transactional(readOnly = true)
    public NewsListDTO aggregate(Actor actor, Long fromEventId) {
        List<NewsEvent> events = getEntityRepository()
                .findAllBySchoolIdAndIdGreaterThan(actor.getSchoolId(), fromEventId);
        return aggregate(events);
    }

    public NewsListDTO aggregate(Integer schoolId, Long fromDate) {
        List<NewsEvent> events = getEntityRepository().findAllBySchoolIdAndIdGreaterThan(schoolId, fromDate);
        return aggregate(events);
    }

    @Transactional(readOnly = true)
    public NewsListDTO aggregate(Set<News> newsSet, Long fromEventId) {
        List<NewsEvent> events = getEntityRepository().findAllByNewsIdInAndIdGreaterThan(newsSet, fromEventId);
        return aggregate(events);
    }

    private NewsListDTO aggregate(List<NewsEvent> events) {
        NewsListDTO listDTO = new NewsListDTO();
        events.forEach(event -> aggregate(listDTO, event));
        return listDTO;
    }

    private void aggregate(NewsListDTO listDTO,
                           NewsEvent event) {
        listDTO.setEventId(event.getId());
        switch (event.getType()) {
            case CREATED: {
                listDTO.addCreated(newsRepository.findOne(event.getParameter()));
                return;
            }
            case DELETED: {
                listDTO.addDeleted(event.getParameter());
                return;
            }
            case UPDATED: {
                listDTO.addUpdated(newsRepository.findOne(event.getParameter()));
                return;
            }
            default:
        }
    }

    @Override
    protected NewsEventRepository getEntityRepository(){
        return (NewsEventRepository) super.getEntityRepository();
    }
}
