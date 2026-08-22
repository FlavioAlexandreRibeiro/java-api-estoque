package com.API_de_Estoque.exception;

public class EstoqueInsuficienteException extends RuntimeException {
  public EstoqueInsuficienteException(String message) {
    super(message);
  }
}
