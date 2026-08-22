package com.API_de_Estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome do produto não pode ser vazio")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição do produto não pode ser vazia")
    @Size(max = 255, message = "A descrição do produto deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "O preço do produto não pode ser nulo")
    @Positive(message = "O preço do produto deve ser positivo")
    private BigDecimal preco;

    @NotNull(message = "O status do produto não pode ser nulo")
    private Boolean ativo;
}
