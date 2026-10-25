package com.API_de_Estoque.service;

import com.API_de_Estoque.dto.EstoqueRequestDTO;
import com.API_de_Estoque.dto.EstoqueResponseDTO;
import com.API_de_Estoque.dto.MovimentacaoEstoqueRequestDTO;
import com.API_de_Estoque.entity.Estoque;
import com.API_de_Estoque.entity.Produto;
import com.API_de_Estoque.exception.*;
import com.API_de_Estoque.repository.EstoqueRepository;
import com.API_de_Estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    private Produto produto;
    private Estoque estoque;
    private EstoqueRequestDTO estoqueRequestDTO;
    private MovimentacaoEstoqueRequestDTO movimentacaoDTO;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do produto teste");
        produto.setPreco(new java.math.BigDecimal("99.99"));
        produto.setAtivo(true);

        estoque = new Estoque();
        estoque.setId(1L);
        estoque.setQuantidade(100);
        estoque.setEstoqueMinimo(10);
        estoque.setProduto(produto);

        estoqueRequestDTO = new EstoqueRequestDTO();
        estoqueRequestDTO.setQuantidade(100);
        estoqueRequestDTO.setEstoqueMinimo(10);
        estoqueRequestDTO.setProdutoId(1L);

        movimentacaoDTO = new MovimentacaoEstoqueRequestDTO();
        movimentacaoDTO.setQuantidade(50);
    }

    @Test
    void cadastrar_Sucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.empty());
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        EstoqueResponseDTO result = estoqueService.cadastrar(estoqueRequestDTO);

        assertNotNull(result);
        assertEquals(estoque.getId(), result.id());
        assertEquals(estoqueRequestDTO.getQuantidade(), result.quantidade());
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).findByProdutoId(1L);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @Test
    void cadastrar_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> estoqueService.cadastrar(estoqueRequestDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void cadastrar_EstoqueJaExiste() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));

        assertThrows(EstoqueJaExisteException.class, () -> estoqueService.cadastrar(estoqueRequestDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).findByProdutoId(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void listar_Sucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Estoque> page = new PageImpl<>(java.util.List.of(estoque));

        when(estoqueRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class))).thenReturn(page);

        Page<EstoqueResponseDTO> result = estoqueService.listar(null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(estoqueRepository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class));
    }

    @Test
    void buscarPorId_Sucesso() {
        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));

        EstoqueResponseDTO result = estoqueService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(estoque.getId(), result.id());
        verify(estoqueRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_EstoqueNaoEncontrado() {
        when(estoqueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () -> estoqueService.buscarPorId(1L));

        verify(estoqueRepository, times(1)).findById(1L);
    }

    @Test
    void atualizar_Sucesso() {
        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        EstoqueResponseDTO result = estoqueService.atualizar(1L, estoqueRequestDTO);

        assertNotNull(result);
        verify(estoqueRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @Test
    void atualizar_EstoqueNaoEncontrado() {
        when(estoqueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () -> estoqueService.atualizar(1L, estoqueRequestDTO));

        verify(estoqueRepository, times(1)).findById(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void deletar_Sucesso() {
        when(estoqueRepository.existsById(1L)).thenReturn(true);
        doNothing().when(estoqueRepository).deleteById(1L);

        estoqueService.deletar(1L);

        verify(estoqueRepository, times(1)).existsById(1L);
        verify(estoqueRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_EstoqueNaoEncontrado() {
        when(estoqueRepository.existsById(1L)).thenReturn(false);

        assertThrows(EstoqueNaoEncontradoException.class, () -> estoqueService.deletar(1L));

        verify(estoqueRepository, times(1)).existsById(1L);
        verify(estoqueRepository, never()).deleteById(anyLong());
    }

    @Test
    void entrada_Sucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        EstoqueResponseDTO result = estoqueService.entrada(1L, movimentacaoDTO);

        assertNotNull(result);
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).findByProdutoId(1L);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @Test
    void entrada_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> estoqueService.entrada(1L, movimentacaoDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void entrada_ProdutoInativo() {
        produto.setAtivo(false);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(ProdutoInativoException.class, () -> estoqueService.entrada(1L, movimentacaoDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void entrada_EstoqueNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () -> estoqueService.entrada(1L, movimentacaoDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).findByProdutoId(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void saida_Sucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        EstoqueResponseDTO result = estoqueService.saida(1L, movimentacaoDTO);

        assertNotNull(result);
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).findByProdutoId(1L);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @Test
    void saida_EstoqueInsuficiente() {
        movimentacaoDTO.setQuantidade(150);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));

        assertThrows(EstoqueInsuficienteException.class, () -> estoqueService.saida(1L, movimentacaoDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).findByProdutoId(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void saida_ProdutoInativo() {
        produto.setAtivo(false);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(ProdutoInativoException.class, () -> estoqueService.saida(1L, movimentacaoDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, never()).save(any(Estoque.class));
    }
}
