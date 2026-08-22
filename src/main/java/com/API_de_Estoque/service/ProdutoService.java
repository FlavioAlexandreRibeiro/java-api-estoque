package com.API_de_Estoque.service;


import com.API_de_Estoque.dto.ProdutoRequestDTO;
import com.API_de_Estoque.dto.ProdutoResponseDTO;
import com.API_de_Estoque.entity.Produto;
import com.API_de_Estoque.exception.ProdutoJaCadastradoException;
import com.API_de_Estoque.exception.ProdutoNaoEncontradoException;
import com.API_de_Estoque.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoResponseDTO cadastrar(ProdutoRequestDTO produtoDto) {

        if (produtoRepository.existsByNome(produtoDto.getNome())) {
            throw new ProdutoJaCadastradoException("Produto já cadastrado");
        }

        Produto produto = new Produto();

        produto.setNome(produtoDto.getNome());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setPreco(produtoDto.getPreco());
        produto.setAtivo(produtoDto.getAtivo());

        Produto produtoSalvo = produtoRepository.save(produto);

        return ProdutoResponseDTO.mapearParaDto(produtoSalvo);
    }


    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::mapearParaDto)
                .toList();
    }


    public ProdutoResponseDTO buscarPorId(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        return ProdutoResponseDTO.mapearParaDto(produto);
    }


    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoDto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o ID: " + id));

        if (!produto.getNome().equals(produtoDto.getNome())
                && produtoRepository.existsByNome(produtoDto.getNome())) {
            throw new ProdutoJaCadastradoException("Produto já cadastrado");
        }

        produto.setNome(produtoDto.getNome());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setPreco(produtoDto.getPreco());
        produto.setAtivo(produtoDto.getAtivo());

        Produto produtoAtualizado = produtoRepository.save(produto);

        return ProdutoResponseDTO.mapearParaDto(produtoAtualizado);
    }


    @Transactional
    public void deletar(Long id) {

        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
