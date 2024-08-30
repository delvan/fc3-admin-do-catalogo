package com.fullcycle.admin.catalogo.infrastructure.category.presenters;

import java.util.function.Function;

import com.fullcycle.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import com.fullcycle.admin.catalogo.infrastructure.category.models.CategoryApiOutput;

public interface CategoryApiPresenter {

    Function<CategoryOutput, CategoryApiOutput> present = output -> new CategoryApiOutput(output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt());

    static CategoryApiOutput present(final CategoryOutput output) {
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt());
    }

}
