package com.projetozero.business.mapper;

import com.projetozero.controller.dto.TarefasDTO;
import com.projetozero.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface TarefasUpdateMapper {

   void  updateTarefas(TarefasDTO dto,@MappingTarget TarefasEntity entity);
}
