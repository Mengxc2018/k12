package cn.k12soft.servo.service.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
@Transactional(readOnly = true)
public abstract class EntityMapper<ENTITY, DTO> {

  public final DTO toDTO(ENTITY entity) {
    return entity == null ? null : convert(entity);
  }

  protected abstract DTO convert(ENTITY entity);

  public Collection<DTO> toDTOs(Collection<ENTITY> entities) {
    return entities.stream()
      .map(this::toDTO)
      .filter(Objects::nonNull)
      .collect(Collectors.toCollection(LinkedList::new));
  }

}
