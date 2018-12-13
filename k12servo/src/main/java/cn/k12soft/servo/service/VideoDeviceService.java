package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.VideoDevice;
import cn.k12soft.servo.repository.VideoDeviceRepository;
import cn.k12soft.servo.web.form.VideoDeviceForm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoDeviceService extends AbstractRepositoryService<VideoDevice, Integer, VideoDeviceRepository> {

  @Autowired
  public VideoDeviceService(VideoDeviceRepository repository) {
    super(repository);
  }

  public VideoDevice createDevice(Integer schoolId, VideoDeviceForm form, Integer creatorId) {
    VideoDevice videoDevice = new VideoDevice(schoolId,
      form.getKlassId(),
      form.getDeviceId(),
      form.getChannelId(),
      form.getRoleId(),
      form.getAuthId(),
      creatorId);
    return getRepository().save(videoDevice);
  }

  @Transactional(readOnly = true)
  public List<VideoDevice> getAllOfKlassId(Integer schoolId, Integer klassId) {
    return getRepository().findAllBySchoolIdAndKlassId(schoolId, klassId);
  }

  @Transactional(readOnly = true)
  public List<VideoDevice> getAllOfCreatorId(Integer schoolId, Integer creatorId) {
    return getRepository().findAllBySchoolIdAndCreatorId(schoolId, creatorId);
  }

  public void delete(Integer schoolId, String deviceId, Integer creatorId) {
    getRepository().deleteBySchoolIdAndDeviceIdAndCreatorId(schoolId, deviceId, creatorId);
  }
}
