package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Application;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

  Optional<Application> findByName(String name);
}
