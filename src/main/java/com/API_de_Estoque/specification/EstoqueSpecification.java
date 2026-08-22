package com.API_de_Estoque.specification;

import com.API_de_Estoque.entity.Estoque;
import org.springframework.data.jpa.domain.Specification;

public class EstoqueSpecification {

    public static Specification<Estoque> quantidadeBetween(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) {
                return cb.conjunction();
            }
            if (min == null) {
                return cb.lessThanOrEqualTo(root.get("quantidade"), max);
            }
            if (max == null) {
                return cb.greaterThanOrEqualTo(root.get("quantidade"), min);
            }
            return cb.between(root.get("quantidade"), min, max);
        };
    }

    public static Specification<Estoque> quantidadeLessThan(Integer quantidade) {
        return (root, query, cb) -> {
            if (quantidade == null) {
                return cb.conjunction();
            }
            return cb.lessThan(root.get("quantidade"), quantidade);
        };
    }

    public static Specification<Estoque> quantidadeGreaterThan(Integer quantidade) {
        return (root, query, cb) -> {
            if (quantidade == null) {
                return cb.conjunction();
            }
            return cb.greaterThan(root.get("quantidade"), quantidade);
        };
    }

    public static Specification<Estoque> estoqueMinimoLessThan(Integer estoqueMinimo) {
        return (root, query, cb) -> {
            if (estoqueMinimo == null) {
                return cb.conjunction();
            }
            return cb.lessThan(root.get("estoqueMinimo"), estoqueMinimo);
        };
    }

    public static Specification<Estoque> estoqueMinimoGreaterThan(Integer estoqueMinimo) {
        return (root, query, cb) -> {
            if (estoqueMinimo == null) {
                return cb.conjunction();
            }
            return cb.greaterThan(root.get("estoqueMinimo"), estoqueMinimo);
        };
    }

    public static Specification<Estoque> quantidadeAbaixoDoMinimo() {
        return (root, query, cb) -> cb.lessThan(root.get("quantidade"), root.get("estoqueMinimo"));
    }

    public static Specification<Estoque> produtoIdEquals(Long produtoId) {
        return (root, query, cb) -> {
            if (produtoId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("produto").get("id"), produtoId);
        };
    }
}
