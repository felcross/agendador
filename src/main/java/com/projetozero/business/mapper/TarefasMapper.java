package com.projetozero.business.mapper;

import com.projetozero.controller.dto.TarefasDTO;
import com.projetozero.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasMapper {
    @Mapping(target = "id", ignore = true)  // ✅ ignore = true (MongoDB gera)
    TarefasEntity paraTarefa(TarefasDTO tarefaDTO);

    TarefasDTO paraTarefaDTO(TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefa(List<TarefasDTO> tarefaDTO);

    List<TarefasDTO> paraListaTarefaDTO(List<TarefasEntity> tarefasEntity);
}
