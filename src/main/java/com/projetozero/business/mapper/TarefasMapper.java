package com.projetozero.business.mapper;

import com.projetozero.controller.dto.TarefasDTO;
import com.projetozero.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefasMapper {
    @Mapping(target = "id", ignore = true)
    TarefasEntity paratarefa(TarefasDTO tarefaDTO);

    TarefasDTO paratarefaDTO(TarefasEntity tarefasEntity);

}
