package com.API_de_Estoque.specification;

import com.API_de_Estoque.entity.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecification {

    public static Specification<Produto> nomeContaining(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Produto> descricaoContaining(String descricao) {
        return (root, query, cb) -> {
            if (descricao == null || descricao.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
        };
    }

    public static Specification<Produto> precoBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) {
                return cb.conjunction();
            }
            if (min == null) {
                return cb.lessThanOrEqualTo(root.get("preco"), max);
            }
            if (max == null) {
                return cb.greaterThanOrEqualTo(root.get("preco"), min);
            }
            return cb.between(root.get("preco"), min, max);
        };
    }

    public static Specification<Produto> precoGreaterThan(Double preco) {
        return (root, query, cb) -> {
            if (preco == null) {
                return cb.conjunction();
            }
            return cb.greaterThan(root.get("preco"), preco);
        };
    }

    public static Specification<Produto> precoLessThan(Double preco) {
        return (root, query, cb) -> {
            if (preco == null) {
                return cb.conjunction();
            }
            return cb.lessThan(root.get("preco"), preco);
        };
    }
}
