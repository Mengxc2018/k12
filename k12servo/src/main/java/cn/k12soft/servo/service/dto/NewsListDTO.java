package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.News;

import java.util.LinkedList;
import java.util.List;

public class NewsListDTO {
    private List<News> createds = new LinkedList<>();
    private List<News> updateds = new LinkedList<>();
    private List<Integer> deleteds = new LinkedList<>();
    private Long eventId;

    public List<News> getCreateds() {
        return createds;
    }

    public List<News> getUpdateds() {
        return updateds;
    }

    public List<Integer> getDeleteds() {
        return deleteds;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void addCreated(News news){
        this.createds.add(news);
    }

    public void addUpdated(News news){
        this.updateds.add(news);
    }

    public void addDeleted(Integer newId){ this.deleteds.add(newId);}
}
