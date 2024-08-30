package com.fullcycle.admin.catalogo.domain.exceptions;

import java.util.Collections;
import java.util.List;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.validation.Error;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> aErrors) {
        super(aMessage, aErrors);
        // TODO Auto-generated constructor stub
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id) {

        final var anError = "%s with ID %s was not found".formatted(anAggregate.getSimpleName(), id.getValue());

        return new NotFoundException(anError, Collections.emptyList());
    }

}
