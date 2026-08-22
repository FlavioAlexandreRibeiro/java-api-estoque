package com.API_de_Estoque.controller;

import com.API_de_Estoque.dto.EstoqueRequestDTO;
import com.API_de_Estoque.dto.EstoqueResponseDTO;
import com.API_de_Estoque.dto.MovimentacaoEstoqueRequestDTO;
import com.API_de_Estoque.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> criar(
            @Valid @RequestBody EstoqueRequestDTO estoqueDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estoqueService.cadastrar(estoqueDto));
    }


    @PostMapping("/{produtoId}/entrada")
    public ResponseEntity<EstoqueResponseDTO> entrada(
            @PathVariable Long produtoId,
            @Valid @RequestBody MovimentacaoEstoqueRequestDTO dto) {
        return ResponseEntity.ok(estoqueService.entrada(produtoId, dto));
    }

    @PostMapping("/{produtoId}/saida/")
    public ResponseEntity<EstoqueResponseDTO> saida(
            @PathVariable Long produtoId,
            @Valid @RequestBody MovimentacaoEstoqueRequestDTO dto) {
        return ResponseEntity.ok(estoqueService.saida(produtoId, dto));
    }


    @GetMapping
    public Page<EstoqueResponseDTO> listar(
            @RequestParam(required = false) Integer quantidade,
            @RequestParam(required = false) Integer estoqueMinimo,
            @RequestParam(required = false) Long produtoId,
            Pageable pageable) {
        return estoqueService.listar(quantidade, estoqueMinimo, produtoId, pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstoqueRequestDTO estoqueDto) {
        return ResponseEntity.ok(estoqueService.atualizar(id, estoqueDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estoqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}