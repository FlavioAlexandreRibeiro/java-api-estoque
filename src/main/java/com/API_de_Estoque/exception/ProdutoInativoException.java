package com.API_de_Estoque.exception;

public class ProdutoInativoException extends RuntimeException {
  public ProdutoInativoException(String message) {
    super(message);
  }
}
