package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Category extends AggregateRoot<CategoryID> implements Cloneable {

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


    public static Category newCategory(final String expectedName,
                                       final String expectedDescription,
                                       final boolean isActive) {

        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, expectedName, expectedDescription, isActive, now, now, deletedAt);
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


    public Category deactivate() {
        if (getDeleteAt() == null) {
            this.deleteAt = Instant.now();
        }

        this.active = false;
        this.updateAt = Instant.now();
        return this;
    }

    public Category activate() {

        this.deleteAt = null;
        this.active = true;
        this.updateAt = Instant.now();
        return this;

    }

    public Category update(final String expectedName,
                           final String expectedDescription,
                           final boolean expectedIsActive) {


        if (expectedIsActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = expectedName;
        this.description = expectedDescription;
        //this.active = expectedIsActive;
        this.updateAt = Instant.now();
        return this;
    }

    public static Category with(final Category aCategory) {
        return new Category(
                aCategory.getId(),
                aCategory.name,
                aCategory.description,
                aCategory.isActive(),
                aCategory.createdAt,
                aCategory.updateAt,
                aCategory.deleteAt
        );


    }


    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}