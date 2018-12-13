package cn.k12soft.servo.service;

import cn.k12soft.servo.error.EntityNotFoundException;
import com.google.common.reflect.TypeToken;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@Transactional
public abstract class AbstractEntityService<E, ID extends Serializable> {

  private final JpaRepository<E, ID> entityRepository;
  private final Class<? super E> entityType;

  public AbstractEntityService(JpaRepository<E, ID> entityRepository) {
    this.entityRepository = entityRepository;
    this.entityType = new TypeToken<E>(getClass()) {
    }.getRawType();
  }

  @Transactional(readOnly = true)
  public List<E> getAll() {
    return entityRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<E> find(ID id) {
    return Optional.ofNullable(entityRepository.findOne(id));
  }

  @Transactional(readOnly = true)
  public E get(ID id) {
    return find(id).orElseThrow(() -> new EntityNotFoundException(entityType, id));
  }

  public E save(E student) {
    return getEntityRepository().save(student);
  }

  public void delete(ID id) {
    entityRepository.delete(id);
  }

  public Optional<E> findDelete(ID id) {
    Optional<E> entity = Optional.ofNullable(entityRepository.findOne(id));
    entity.ifPresent(entityRepository::delete);
    return entity;
  }

  protected JpaRepository<E, ID> getEntityRepository() {
    return entityRepository;
  }
}
