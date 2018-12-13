package cn.k12soft.servo.service.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
public class KlassFeedDTO {

  private Long id;
  private Integer klassId;
  private String klassName;
  private String content;
  private List<String> pictures = new ArrayList<String>();
  private UserBasicDTO createdBy;
  private Instant createdAt;

  public Long getId() {
    return id;
  }

  public KlassFeedDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public KlassFeedDTO setKlassId(Integer klassId) {
    this.klassId = klassId;
    return this;
  }

  public String getContent() {
    return content;
  }

  public KlassFeedDTO setContent(String content) {
    this.content = content;
    return this;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public KlassFeedDTO setPictures(List<String> pictures) {
    //this.pictures = pictures;
    this.pictures.addAll(pictures);
    return this;
  }

  public UserBasicDTO getCreatedBy() {
    return createdBy;
  }

  public KlassFeedDTO setCreatedBy(UserBasicDTO createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public KlassFeedDTO setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public String getKlassName() {
    return klassName;
  }

  public KlassFeedDTO setKlassName(String klassName) {
    this.klassName = klassName;
    return this;
  }
}
