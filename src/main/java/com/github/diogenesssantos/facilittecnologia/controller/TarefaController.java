package com.github.diogenesssantos.facilittecnologia.controller;

import com.github.diogenesssantos.facilittecnologia.assembler.AssemblerTarefa;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private TarefaService tarefaService;


    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> buscarTodas() {
        var listTarefaBD = tarefaService.buscarTodas();
        var listTarefaResponseDTO = AssemblerTarefa.listModelToListDTO(listTarefaBD);
        return ResponseEntity.ok(listTarefaResponseDTO);
    }

}



