package com.github.diogenesssantos.facilittecnologia.controller;

import com.github.diogenesssantos.facilittecnologia.assembler.AssemblerTarefa;
import com.github.diogenesssantos.facilittecnologia.controller.request.TarefaRequestDTO;
import com.github.diogenesssantos.facilittecnologia.controller.response.TarefaResponseDTO;
import com.github.diogenesssantos.facilittecnologia.docs.TarefaDocumentacaoOpenAPI;
import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import com.github.diogenesssantos.facilittecnologia.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
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

    @GetMapping("/status")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTodasPorStatus(@RequestParam(name = "status")
                                                                        Status status) {
        Status.validoOuThrows(status);
        var listTarefaBD = tarefaService.buscarPorStatus(status);
        var listTarefaResponseDTO = AssemblerTarefa.listModelToListDTO(listTarefaBD);

        return ResponseEntity.status(HttpStatus.OK).body(listTarefaResponseDTO);
    }

    @GetMapping("/descricao")
    public ResponseEntity<TarefaResponseDTO> buscarTodasPorStatus(@RequestParam(name = "descricao")
                                                                  String descricao) {

        var listTarefaBD = tarefaService.buscarPorDescricao(descricao);
        var tarefaResponseDTO = AssemblerTarefa.modelToDTO(listTarefaBD);

        return ResponseEntity.status(HttpStatus.OK).body(tarefaResponseDTO);
    }


    @PostMapping
    public ResponseEntity<TarefaResponseDTO> salvar(@RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Tarefa tarefa = AssemblerTarefa.dtoToModel(tarefaRequestDTO);
        Tarefa TarefaBD = tarefaService.salvar(tarefa);
        TarefaResponseDTO tarefaResponseDTO = AssemblerTarefa.modelToDTO(TarefaBD);

        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarPorId(@PathVariable(name = "id") Long id,
                                                            @RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Tarefa tarefaBD = tarefaService.buscarPorId(id);
        tarefaBD = tarefaService.atualizar(tarefaBD, tarefaRequestDTO);
        TarefaResponseDTO tarefaResponseDTO = AssemblerTarefa.modelToDTO(tarefaBD);

        return ResponseEntity.status(HttpStatus.OK).body(tarefaResponseDTO);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarStatusPorId(@PathVariable(name = "id") Long id,
                                                                  @RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Status.validoOuThrows(tarefaRequestDTO.status());
        Tarefa tarefaBD = tarefaService.buscarPorId(id);
        tarefaBD = tarefaService.atualizar(tarefaBD, tarefaRequestDTO);
        TarefaResponseDTO tarefaResponseDTO = AssemblerTarefa.modelToDTO(tarefaBD);

        return ResponseEntity.status(HttpStatus.OK).body(tarefaResponseDTO);
    }


}



