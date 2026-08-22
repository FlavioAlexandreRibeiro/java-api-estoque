package com.API_de_Estoque.repository;

import com.API_de_Estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNome(String nome);

    boolean existsByNome(String nome);
}
