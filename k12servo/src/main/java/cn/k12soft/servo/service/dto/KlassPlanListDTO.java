package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.KlassPlan;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/10/17.
 */
public class KlassPlanListDTO {

  private LinkedList<KlassPlan> createds = new LinkedList<>();
  private LinkedList<KlassPlan> updateds = new LinkedList<>();
  private LinkedList<Integer> deleteds = new LinkedList<>();
  private Long eventId;

  public List<KlassPlan> getCreateds() {
    return createds;
  }

  public KlassPlanListDTO addCreated(KlassPlan klassPlan) {
    createds.addFirst(klassPlan);
    return this;
  }

  public List<KlassPlan> getUpdateds() {
    return updateds;
  }

  public KlassPlanListDTO addUpdated(KlassPlan klassPlan) {
    updateds.addFirst(klassPlan);
    return this;
  }

  public List<Integer> getDeleteds() {
    return deleteds;
  }

  public KlassPlanListDTO addDeleted(Integer id) {
    this.deleteds.addFirst(id);
    return this;
  }

  public Long getEventId() {
    return eventId;
  }

  public KlassPlanListDTO setEventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }
}
