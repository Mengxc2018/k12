package cn.k12soft.servo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class VideoDevice extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  private Integer klassId;
  private String deviceId;
  private String channelId;
  private String roleId;
  private Long authId;
  private Integer creatorId;

  VideoDevice() {
  }

  public VideoDevice(Integer schoolId,
                     Integer klassId,
                     String deviceId,
                     String channelId,
                     String roleId,
                     Long authId,
                     Integer creatorId) {
    super(schoolId);
    this.klassId = klassId;
    this.deviceId = deviceId;
    this.channelId = channelId;
    this.roleId = roleId;
    this.authId = authId;
    this.creatorId = creatorId;
  }

  public Integer getId() {
    return id;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getChannelId() {
    return channelId;
  }

  public String getRoleId() {
    return roleId;
  }

  public Long getAuthId() {
    return authId;
  }

  public Integer getCreatorId() {
    return creatorId;
  }
}
