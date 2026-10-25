package com.API_de_Estoque.service;

import com.API_de_Estoque.dto.ProdutoRequestDTO;
import com.API_de_Estoque.dto.ProdutoResponseDTO;
import com.API_de_Estoque.entity.Produto;
import com.API_de_Estoque.exception.ProdutoJaCadastradoException;
import com.API_de_Estoque.exception.ProdutoNaoEncontradoException;
import com.API_de_Estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private ProdutoRequestDTO produtoRequestDTO;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do produto teste");
        produto.setPreco(new BigDecimal("99.99"));
        produto.setAtivo(true);

        produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setNome("Produto Teste");
        produtoRequestDTO.setDescricao("Descrição do produto teste");
        produtoRequestDTO.setPreco(new BigDecimal("99.99"));
        produtoRequestDTO.setAtivo(true);
    }

    @Test
    void cadastrar_Sucesso() {
        when(produtoRepository.existsByNome(produtoRequestDTO.getNome())).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO result = produtoService.cadastrar(produtoRequestDTO);

        assertNotNull(result);
        assertEquals(produto.getId(), result.id());
        assertEquals(produtoRequestDTO.getNome(), result.nome());
        verify(produtoRepository, times(1)).existsByNome(produtoRequestDTO.getNome());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void cadastrar_ProdutoJaCadastrado() {
        when(produtoRepository.existsByNome(produtoRequestDTO.getNome())).thenReturn(true);

        assertThrows(ProdutoJaCadastradoException.class, () -> produtoService.cadastrar(produtoRequestDTO));

        verify(produtoRepository, times(1)).existsByNome(produtoRequestDTO.getNome());
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void listar_Sucesso() {
        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto Teste 2");
        produto2.setDescricao("Descrição do produto teste 2");
        produto2.setPreco(new BigDecimal("149.99"));
        produto2.setAtivo(true);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto, produto2));

        List<ProdutoResponseDTO> result = produtoService.listar();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(produto.getId(), result.get(0).id());
        assertEquals(produto2.getId(), result.get(1).id());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Sucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoResponseDTO result = produtoService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(produto.getId(), result.id());
        assertEquals(produto.getNome(), result.nome());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.buscarPorId(1L));

        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void atualizar_Sucesso() {
        ProdutoRequestDTO novoDTO = new ProdutoRequestDTO();
        novoDTO.setNome("Produto Atualizado");
        novoDTO.setDescricao("Descrição atualizada");
        novoDTO.setPreco(new BigDecimal("149.99"));
        novoDTO.setAtivo(true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.existsByNome(novoDTO.getNome())).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO result = produtoService.atualizar(1L, novoDTO);

        assertNotNull(result);
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void atualizar_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.atualizar(1L, produtoRequestDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void atualizar_NomeJaEmUso() {
        ProdutoRequestDTO novoDTO = new ProdutoRequestDTO();
        novoDTO.setNome("Outro Nome");
        novoDTO.setDescricao("Descrição");
        novoDTO.setPreco(new BigDecimal("99.99"));
        novoDTO.setAtivo(true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.existsByNome(novoDTO.getNome())).thenReturn(true);

        assertThrows(ProdutoJaCadastradoException.class, () -> produtoService.atualizar(1L, novoDTO));

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void deletar_Sucesso() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deletar(1L);

        verify(produtoRepository, times(1)).existsById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_ProdutoNaoEncontrado() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.deletar(1L));

        verify(produtoRepository, times(1)).existsById(1L);
        verify(produtoRepository, never()).deleteById(anyLong());
    }
}
