package com.projetozero.infrastructure.repository;

import com.projetozero.infrastructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity,String> {

     List<TarefasEntity> findByDataAgendamentoBetween(LocalDateTime dataInicio,LocalDateTime dataFinal);

     List<TarefasEntity> findByEmailUsuario(String email);

     Optional<TarefasEntity> findBy(String id);
}
