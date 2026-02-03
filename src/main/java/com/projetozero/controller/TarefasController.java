package com.projetozero.controller;


import com.projetozero.business.TarefaService;
import com.projetozero.controller.dto.TarefasDTO;
import com.projetozero.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefaService tarefaService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravartarefa(@RequestBody TarefasDTO tarefaDTO,
                                                   @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(tarefaService.gravartarefa(token,tarefaDTO));
    }
}
