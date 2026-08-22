package com.API_de_Estoque.exception;

public class EstoqueJaExisteException extends RuntimeException {
  public EstoqueJaExisteException(String message) {
    super(message);
  }
}
