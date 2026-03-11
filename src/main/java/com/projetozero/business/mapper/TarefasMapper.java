package com.projetozero.business.mapper;

import com.projetozero.controller.dto.TarefasDTORecord;
import com.projetozero.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasMapper {
    @Mapping(target = "id", ignore = true)  // ✅ ignore = true (MongoDB gera)
    TarefasEntity paraTarefa(TarefasDTORecord tarefaDTO);

    TarefasDTORecord paraTarefaDTO(TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefa(List<TarefasDTORecord> tarefaDTO);

    List<TarefasDTORecord> paraListaTarefaDTO(List<TarefasEntity> tarefasEntity);

}
