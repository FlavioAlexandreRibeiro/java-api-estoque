package com.API_de_Estoque.dto;

import com.API_de_Estoque.entity.Estoque;

public record EstoqueResponseDTO(

        Long id,
        Integer quantidade,
        Integer estoqueMinimo
) {

    public static EstoqueResponseDTO mapearParaDto(Estoque estoque) {
        return new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getQuantidade(),
                estoque.getEstoqueMinimo()
        );
    }
}
