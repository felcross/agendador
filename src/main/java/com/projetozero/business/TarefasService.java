package com.projetozero.business;

import com.projetozero.business.mapper.TarefasMapper;
import com.projetozero.business.mapper.TarefasUpdateMapper;
import com.projetozero.controller.dto.TarefasDTORecord;

import com.projetozero.infrastructure.Enums.StatusTarefaEnum;

import com.projetozero.infrastructure.entity.TarefasEntity;
import com.projetozero.infrastructure.exception.ResourceNotFoundException;
import com.projetozero.infrastructure.repository.TarefasRepository;
import com.projetozero.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {


    private final TarefasRepository tarefaRepository;
    private final TarefasMapper tarefaMapper;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateMapper updateMapper;


    public TarefasDTORecord gravarTarefa(String token, TarefasDTORecord dto) {
        String email = jwtUtil.extrairEmailToken(token.replace("Bearer ", "").trim());
        // 1. Pega a hora atual "crua" do sistema
        LocalDateTime agora = LocalDateTime.now();

// 2. Verifica se o sistema está operando em UTC (comum em Docker/WSL)
        if (ZoneId.systemDefault().getId().contains("UTC") || ZoneId.systemDefault().getId().equals("Z")) {
            // Se for UTC, subtrai 3 horas para chegar no horário de Brasília
            agora = agora.minusHours(3);
        }

        // Reconstrói o record com os novos valores
        TarefasDTORecord dtoCompleto = new TarefasDTORecord(
                dto.id(),
                dto.nomeTarefa(),
                dto.descricao(),
                agora,        // dataCriacao
                dto.dataAgendamento(),
                email,                      // emailUsuario
                dto.dataAlteracao(),
                StatusTarefaEnum.PENDENTE   // status
        );

        TarefasEntity tarefa = tarefaMapper.paraTarefa(dtoCompleto);
        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(tarefa));
    }


    public List<TarefasDTORecord> buscaListaDeTarefasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFinal) {
        return tarefaMapper.paraListaTarefaDTO(tarefaRepository.findByDataAgendamentoBetweenAndStatus(dataInicio, dataFinal,StatusTarefaEnum.PENDENTE));
    }


    public List<TarefasDTORecord> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaMapper.paraListaTarefaDTO(tarefaRepository.findByEmailUsuario(email));
    }


    public void deletaTarefaPorId(String id) {
        try {
            tarefaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {

            throw new ResourceNotFoundException("Id inexistente" + id, e.getCause());
        }
    }

    public TarefasDTORecord alteraStatus(String id,StatusTarefaEnum status) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            entity.setStatus(status);
            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {

            throw new ResourceNotFoundException("Erro ao alterar tarefa" + id, e.getCause());
        }

    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord dto, String id) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException(id + "Não encontrada"));
            updateMapper.updateTarefas(dto,entity);
            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {

            throw new ResourceNotFoundException("Erro ao alterar tarefa" + id, e.getCause());
        }

    }
}
