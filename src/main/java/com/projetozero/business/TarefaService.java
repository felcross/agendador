package com.projetozero.business;

import com.projetozero.business.mapper.TarefasMapper;
import com.projetozero.controller.dto.TarefasDTO;
import com.projetozero.infrastructure.Enums.StatusTarefaEnum;
import com.projetozero.infrastructure.entity.TarefasEntity;
import com.projetozero.infrastructure.repository.TarefasRepository;
import com.projetozero.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {


    private final TarefasRepository tarefaRepository;
    private final TarefasMapper tarefaMapper;
    private final JwtUtil jwtUtil;


    public TarefasDTO gravartarefa(String token,TarefasDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(StatusTarefaEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity tarefa =  tarefaMapper.paratarefa(dto);
        return tarefaMapper.paratarefaDTO(tarefaRepository.save(tarefa)) ;
    }
}
