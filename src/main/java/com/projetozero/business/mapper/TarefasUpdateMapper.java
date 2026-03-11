package com.projetozero.business.mapper;

import com.projetozero.controller.dto.TarefasDTORecord;
import com.projetozero.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface TarefasUpdateMapper {

   void  updateTarefas(TarefasDTORecord dto,@MappingTarget TarefasEntity entity);

}
