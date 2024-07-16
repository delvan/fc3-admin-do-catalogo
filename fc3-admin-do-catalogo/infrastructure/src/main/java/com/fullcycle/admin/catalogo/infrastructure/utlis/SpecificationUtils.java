package com.fullcycle.admin.catalogo.infrastructure.utlis;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {


    private SpecificationUtils() {

    }

    public static <T> Specification<T> like(final String prop, final String terms) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get(prop)), like(terms.toUpperCase()));
    }

    private static String like(final String terms) {
        return "%" + terms + "%";
    }

}
