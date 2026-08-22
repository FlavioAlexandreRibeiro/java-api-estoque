package com.API_de_Estoque.repository;

import com.API_de_Estoque.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>,
        JpaSpecificationExecutor<Estoque> {

    Optional<Estoque> findByProdutoId(Long produtoId);

    List<Estoque> findByQuantidadeLessThan(Integer quantidade);

    List<Estoque> findByQuantidadeBetween(Integer min, Integer max);
}
