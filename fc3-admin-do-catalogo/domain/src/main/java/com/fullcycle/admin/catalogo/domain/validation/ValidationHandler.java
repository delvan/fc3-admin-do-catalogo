package com.fullcycle.admin.catalogo.domain.validation;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;

import java.util.List;

//
public interface ValidationHandler {

    //interface fluente: possibilidade de chamar um metodo, retornar a propria
    // instancia, encadeano chamadas de metodos
    ValidationHandler append(Error anError) throws DomainException;

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler validate(Validation aValidate);

    List<Error> getErrors();

    default boolean hasError(){

        return getErrors() != null && !getErrors().isEmpty();
    }

    public interface Validation{
        void validate();
    }
}
