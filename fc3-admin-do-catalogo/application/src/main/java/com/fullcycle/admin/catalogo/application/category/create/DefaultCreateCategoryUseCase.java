package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.validation.handle.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    //--------------Gateway-----------------------//
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand anCommand) {

        final var aName = anCommand.name();
        final var aDescription = anCommand.description();
        final var aIsActive = anCommand.isActive();

        final var notification = Notification.create();

        final var aCategory = Category.newCategory(aName, aDescription, aIsActive);

        aCategory.validate(notification);

        return notification.hasError() ? API.Left(notification) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {
        return Try(() -> this.categoryGateway
                .create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
             

    }
}
