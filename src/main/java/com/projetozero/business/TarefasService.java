package com.projetozero.business;

import com.projetozero.business.mapper.TarefasMapper;
import com.projetozero.business.mapper.TarefasUpdateMapper;
import com.projetozero.controller.dto.TarefasDTO;

import com.projetozero.infrastructure.Enums.StatusTarefaEnum;

import com.projetozero.infrastructure.entity.TarefasEntity;
import com.projetozero.infrastructure.exception.ResourceNotFoundException;
import com.projetozero.infrastructure.repository.TarefasRepository;
import com.projetozero.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {


    private final TarefasRepository tarefaRepository;
    private final TarefasMapper tarefaMapper;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateMapper updateMapper;


    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.replace("Bearer ", "").trim());
        // Configura os dados da tarefa
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(StatusTarefaEnum.PENDENTE);
        dto.setEmailUsuario(email);
        // Salva e retorna
        TarefasEntity tarefa = tarefaMapper.paraTarefa(dto);
        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(tarefa));
    }

    public List<TarefasDTO> buscaListaDeTarefasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFinal) {
        return tarefaMapper.paraListaTarefaDTO(tarefaRepository.findByDataAgendamentoBetween(dataInicio, dataFinal));
    }


    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
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

    public TarefasDTO alteraStatus(String id,StatusTarefaEnum status) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            entity.setStatus(status);
            return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {

            throw new ResourceNotFoundException("Erro ao alterar tarefa" + id, e.getCause());
        }

    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
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
