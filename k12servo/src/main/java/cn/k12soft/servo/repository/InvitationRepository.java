package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Invitation;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

  Optional<Invitation> findOneByMobile(String mobile);

  List<Invitation> findByMobile(String mobile);
}
