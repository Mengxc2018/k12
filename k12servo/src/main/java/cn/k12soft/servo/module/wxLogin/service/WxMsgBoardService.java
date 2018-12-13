package cn.k12soft.servo.module.wxLogin.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.wxLogin.domain.WxMsgBoard;
import cn.k12soft.servo.module.wxLogin.domain.form.WxMsgBoardForm;
import cn.k12soft.servo.module.wxLogin.repository.WxMsgBoardRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

@Service
@Transactional
public class WxMsgBoardService extends AbstractRepositoryService<WxMsgBoard, Long, WxMsgBoardRepository> {

    protected WxMsgBoardService(WxMsgBoardRepository repository) {
        super(repository);
    }

    public Collection<WxMsgBoard> findAllBySchoolId(Actor actor, LocalDate date) {
        Integer schoolId = actor.getSchoolId();
        Instant first = date.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = date.withDayOfMonth(date.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        return getRepository().findAllBySchoolIdAndCreatedAtBetween(schoolId, first, second);
    }

    public WxMsgBoard created(Actor actor, WxMsgBoardForm form) {
        WxMsgBoard wxMsgBoard = new WxMsgBoard(
                form.getMassage(),
                actor,
                actor.getSchoolId(),
                Instant.now()
        );
        return getRepository().save(wxMsgBoard);
    }
}
