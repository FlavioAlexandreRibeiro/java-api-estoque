package com.API_de_Estoque.dto;

import com.API_de_Estoque.entity.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(

        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean ativo

) {

    public static ProdutoResponseDTO mapearParaDto(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.isAtivo()
        );
    }
}
