package cn.k12soft.servo.module.attendanceCount.domain.dto;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public abstract class EntityMapperToList<ENTITY, DTO> {

    public final DTO toDTO(ENTITY entity) {
        return entity == null ? null : convert(entity);
    }

    protected abstract DTO convert(ENTITY entity);

    public List<DTO> toDTOs(Collection<ENTITY> entities) {
        return entities.stream()
                .map(this::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
