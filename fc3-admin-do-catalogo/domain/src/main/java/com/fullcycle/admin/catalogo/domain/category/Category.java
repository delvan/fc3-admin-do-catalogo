package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;
    private String description;
    private boolean active;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


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
        return updatedAt;
    }

    public Instant getDeleteAt() {
        return deletedAt;
    }


    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updateAt' should not be null");
        this.deletedAt = aDeleteDate;
    }


    public static Category newCategory(final String expectedName,
                                       final String expectedDescription,
                                       final boolean isActive) {

        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, expectedName, expectedDescription, isActive, now, now, deletedAt);
    }

    public static Category with(
            final CategoryID anId,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        return new Category(
                anId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    @Override
    public void validate(ValidationHandler handler) {

        new CategoryValidator(this, handler).validate();

    }


    public Category deactivate() {
        if (getDeleteAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {

        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;

    }

    public Category update(final String aName,
                           final String aDescription,
                           final boolean isActive) {


        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        //this.active = expectedIsActive;
        
        
        this.updatedAt = Instant.now();
        return this;
    }

    public static Category with(final Category aCategory) {
        return with(
                aCategory.getId(),
                aCategory.name,
                aCategory.description,
                aCategory.isActive(),
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
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