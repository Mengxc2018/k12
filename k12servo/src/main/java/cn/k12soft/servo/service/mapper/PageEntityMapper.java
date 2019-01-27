package cn.k12soft.servo.service.mapper;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
@Transactional(readOnly = true)
public abstract class PageEntityMapper<ENTITY, DTO> {

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

//  private Page<DTO> toPageDTOs(Page<ENTITY> entityPage){
//
//    return entityPage.getContent().stream()
//            .map(this::toDTO)
//            .filter(Objects::nonNull)
//            .collect(Page<ENTITY>::new);
//
//      LinkedList<DTO> collect = dtos.stream()
//              .map(this::toDTO)
//              .filter(Objects::nonNull)
//              .collect(Collectors.toCollection(LinkedList::new));
//      Page<DTO> page = null;
//    page.


}
