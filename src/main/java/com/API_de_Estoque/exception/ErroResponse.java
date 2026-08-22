package com.API_de_Estoque.exception;

import java.time.LocalDateTime;

public record ErroResponse (
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem
){
}
