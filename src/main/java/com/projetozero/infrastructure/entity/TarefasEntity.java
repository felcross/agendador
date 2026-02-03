package com.projetozero.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projetozero.infrastructure.Enums.StatusTarefaEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("tarefa")
public class TarefasEntity {

    @Id
    private String id;

    private String nomeTarefa;

    private String descricao;

    private LocalDateTime dataCriacao;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAgendamento;

    private String emailUsuario;

    private LocalDateTime dataAlteracao;

    private StatusTarefaEnum status;

}