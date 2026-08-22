package com.API_de_Estoque.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueRequestDTO {


    @NotNull(message = "A quantidade do estoque não pode ser nula")
    @PositiveOrZero(message = "A quantidade do estoque não pode ser negativa")
    private Integer quantidade;

    @NotNull(message = "O estoque mínimo não pode ser nulo")
    @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
    private Integer estoqueMinimo;

    @NotNull(message = "O ID do produto é obrigatório")
    private Long produtoId;
}
