package com.API_de_Estoque.service;


import com.API_de_Estoque.dto.EstoqueRequestDTO;
import com.API_de_Estoque.dto.EstoqueResponseDTO;
import com.API_de_Estoque.entity.Estoque;
import com.API_de_Estoque.entity.Produto;
import com.API_de_Estoque.exception.EstoqueJaExisteException;
import com.API_de_Estoque.exception.EstoqueNaoEncontradoException;
import com.API_de_Estoque.exception.ProdutoNaoEncontradoException;
import com.API_de_Estoque.repository.EstoqueRepository;
import com.API_de_Estoque.repository.ProdutoRepository;
import com.API_de_Estoque.specification.EstoqueSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public EstoqueResponseDTO cadastrar(EstoqueRequestDTO estoqueDto) {

        Produto produto = produtoRepository.findById(estoqueDto.getProdutoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        "Produto não encontrado com ID: " + estoqueDto.getProdutoId()));

        if (estoqueRepository.findByProdutoId(estoqueDto.getProdutoId()).isPresent()) {
            throw new EstoqueJaExisteException("Estoque já existe para este produto");
        }

        Estoque estoque = new Estoque();

        estoque.setQuantidade(estoqueDto.getQuantidade());
        estoque.setEstoqueMinimo(estoqueDto.getEstoqueMinimo());
        estoque.setProduto(produto);

        Estoque estoqueSalvo = estoqueRepository.save(estoque);

        return EstoqueResponseDTO.mapearParaDto(estoqueSalvo);
    }


    public Page<EstoqueResponseDTO> listar(
            Integer quantidade,
            Integer estoqueMinimo,
            Long produtoId,
            Pageable pageable) {

        Specification<Estoque> spec = ((root, query,
                                        criteriaBuilder) ->
                criteriaBuilder.conjunction());

        if (quantidade != null) {
            spec = spec.and((r, q, cb) ->
                    cb.equal(r.get("quantidade"), quantidade));
        }

        if (estoqueMinimo != null) {
            spec = spec.and((r, q, cb) ->
                    cb.equal(r.get("estoqueMinimo"), estoqueMinimo));
        }

        if (produtoId != null) {
            spec = spec.and(EstoqueSpecification.produtoIdEquals(produtoId));
        }

        return estoqueRepository.findAll(spec, pageable)
                .map(EstoqueResponseDTO::mapearParaDto);
    }


    public EstoqueResponseDTO buscarPorId(Long id) {

        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new EstoqueNaoEncontradoException(
                        "Estoque não encontrado com ID: " + id));

        return EstoqueResponseDTO.mapearParaDto(estoque);
    }


    @Transactional
    public EstoqueResponseDTO atualizar(Long id, EstoqueRequestDTO estoqueDto) {

        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new EstoqueNaoEncontradoException(
                        "Estoque não encontrado com ID: " + id));

        Produto produto = produtoRepository.findById(estoqueDto.getProdutoId())
                .orElseThrow(()-> new ProdutoNaoEncontradoException(
                        "Produto não encontrado com ID: " + estoqueDto.getProdutoId()));

        estoque.setQuantidade(estoqueDto.getQuantidade());
        estoque.setEstoqueMinimo(estoqueDto.getEstoqueMinimo());
        estoque.setProduto(produto);

        Estoque estoqueAtualizado = estoqueRepository.save(estoque);

        return EstoqueResponseDTO.mapearParaDto(estoqueAtualizado);
    }


    @Transactional
    public void deletar(Long id) {

        if (!estoqueRepository.existsById(id)) {
            throw new EstoqueNaoEncontradoException("Estoque não encontrado com ID: " + id);
        }
        estoqueRepository.deleteById(id);
    }
}
