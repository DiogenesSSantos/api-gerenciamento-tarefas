package com.github.diogenesssantos.facilittecnologia.controller;

import com.github.diogenesssantos.facilittecnologia.assembler.AssemblerTarefa;
import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.docs.TarefaDocumentacaoOpenAPI;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController implements TarefaDocumentacaoOpenAPI {

    private TarefaService tarefaService;


    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> buscarTodas() {
        var listTarefaBD = tarefaService.buscarTodas();
        var listTarefaResponseDTO = AssemblerTarefa.listModelToListDTO(listTarefaBD);

        return ResponseEntity.status(HttpStatus.OK).body(listTarefaResponseDTO);
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> salvar(@RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Tarefa tarefa = AssemblerTarefa.dtoToModel(tarefaRequestDTO);
        Tarefa TarefaBD = tarefaService.salvar(tarefa);
        TarefaResponseDTO tarefaResponseDTO = AssemblerTarefa.modelToDTO(TarefaBD);

        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarPorId(@PathVariable(name = "id") Long id ,
                                                            @RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Tarefa tarefaBD = tarefaService.buscarPorId(id);
        tarefaBD = tarefaService.atualizar(tarefaBD, tarefaRequestDTO);
        TarefaResponseDTO tarefaResponseDTO = AssemblerTarefa.modelToDTO(tarefaBD);

        return ResponseEntity.status(HttpStatus.OK).body(tarefaResponseDTO);
    }

}



