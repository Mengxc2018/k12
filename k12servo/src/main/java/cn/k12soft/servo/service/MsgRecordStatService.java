package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.MsgRecordStat;
import cn.k12soft.servo.domain.MsgRecordStat.MsgType;
import cn.k12soft.servo.repository.MsgRecordStatRepository;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MsgRecordStatService extends AbstractRepositoryService<MsgRecordStat, Long, MsgRecordStatRepository> {

  @Autowired
  protected MsgRecordStatService(MsgRecordStatRepository repository) {
    super(repository);
  }

  @Transactional
  public int recordAndCount(Integer schoolId, MsgType type, Integer klassId, Long msgId, Integer userId) {
    MsgRecordStatRepository repository = getRepository();
    if (!repository.existsByTypeAndMsgIdAndUserId(type, msgId, userId)) {
      repository.save(new MsgRecordStat(schoolId, type, klassId, msgId, userId, Instant.now()));
    }
    return repository.countAllByTypeAndMsgId(type, msgId);
  }

  @Transactional(readOnly = true)
  public Map<Long, Integer> queryStats(MsgType type, List<Long> feedIds) {
    Map<Long, Integer> counts = new HashMap<>(feedIds.size());
    for (Long feedId : feedIds) {
      counts.put(feedId, getRepository().countAllByTypeAndMsgId(type, feedId));
    }
    return counts;
  }
}
