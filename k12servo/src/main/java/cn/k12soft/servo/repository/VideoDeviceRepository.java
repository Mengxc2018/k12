package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.VideoDevice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoDeviceRepository extends JpaRepository<VideoDevice, Integer> {

  List<VideoDevice> findAllBySchoolIdAndKlassId(Integer schoolId, Integer klassId);

  List<VideoDevice> findAllBySchoolIdAndCreatorId(Integer schoolId, Integer creatorId);

  void deleteBySchoolIdAndDeviceIdAndCreatorId(Integer schoolId, String deviceId, Integer creatorId);
}