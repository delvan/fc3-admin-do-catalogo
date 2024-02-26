package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.handle.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryCommand anCommand) {
        final var anId = CategoryID.from(anCommand.id());
        final var aName = anCommand.name();
        final var aDescription = anCommand.description();
        final var isActive = anCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        aCategory.update(aName, aDescription, isActive).validate(notification);

        return notification.hasError() ? API.Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.categoryGateway
                .update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private static Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with ID %s was not-found".formatted(anId.getValue())));
    }
}
