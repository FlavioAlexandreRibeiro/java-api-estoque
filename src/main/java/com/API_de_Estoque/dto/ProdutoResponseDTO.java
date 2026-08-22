package com.API_de_Estoque.dto;

import com.API_de_Estoque.entity.Produto;

public record ProdutoResponseDTO(

        Long id,
        String nome,
        String descricao,
        Double preco

) {

    public static ProdutoResponseDTO mapearParaDto(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco()
        );
    }
}
