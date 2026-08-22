package com.API_de_Estoque.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoEstoqueRequestDTO {

    @NotNull(message = "A quantidade da movimentação não pode ser nula")
    @Positive(message = "A quantidade da movimentação deve ser maior que zero")
    private Integer quantidade;
}
