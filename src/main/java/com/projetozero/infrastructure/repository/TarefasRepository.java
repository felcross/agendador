package com.projetozero.infrastructure.repository;

import com.projetozero.infrastructure.Enums.StatusTarefaEnum;
import com.projetozero.infrastructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity,String> {

     List<TarefasEntity> findByDataAgendamentoBetweenAndStatus(LocalDateTime dataInicio,
                                                               LocalDateTime dataFinal,
                                                               StatusTarefaEnum status);

     List<TarefasEntity> findByEmailUsuario(String email);

     Optional<TarefasEntity> findById(String id);
}
