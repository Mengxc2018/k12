package cn.k12soft.servo.service;

import cn.k12soft.servo.error.EntityNotFoundException;
import com.google.common.reflect.TypeToken;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Transactional
public class AbstractRepositoryService<E, ID extends Serializable, R extends JpaRepository<E, ID>> {

  private final R repository;
  private final Class<? super E> entityType;

  protected AbstractRepositoryService(R repository) {
    this.repository = repository;
    this.entityType = new TypeToken<E>(getClass()) {}.getRawType();
  }

  @Transactional(readOnly = true)
  public List<E> getAll() {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<E> find(ID id) {
    return Optional.ofNullable(repository.findOne(id));
  }

  @Transactional(readOnly = true)
  public E get(ID id) {
    return find(id).orElseThrow(() -> new EntityNotFoundException(entityType, id));
  }

  public E save(E student) {
    return getRepository().save(student);
  }

  public void delete(ID id) {
    repository.delete(id);
  }

  public Optional<E> findDelete(ID id) {
    Optional<E> entity = Optional.ofNullable(repository.findOne(id));
    entity.ifPresent(repository::delete);
    return entity;
  }

  protected final R getRepository() {
    return repository;
  }
}

