package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean active;

    private Instant createdAt;
    private Instant updateAt;
    private Instant deleteAt;

    private Category(
                    final CategoryID anId,
                    final String aName,
                    final String aDescription,
                    final boolean isActive,
                    final Instant aCreatedAt,
                    final Instant aUpdateAt,
                    final Instant aDeleteAt
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updateAt = aUpdateAt;
        this.deleteAt = aDeleteAt;
    }


    public static Category newCategory(final String expectedName, final String expectedDescription, final boolean expectedIsActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        return new Category(id, expectedName, expectedDescription, expectedIsActive, now, now, null);
    }

    @Override
    public void validate(ValidationHandler handler) {

        new CategoryValidator(this, handler).validate();

    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public Instant getDeleteAt() {
        return deleteAt;
    }
}