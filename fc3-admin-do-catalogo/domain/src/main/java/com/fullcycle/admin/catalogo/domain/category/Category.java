package com.fullcycle.admin.catalogo.domain.category;

import java.time.Instant;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Category {

    private String id;
    private String name;
    private String description;
    private boolean active;

    private Instant createdAt;
    private Instant updateAt;
    private Instant deleteAt;

    private Category(
                    final String id,
                    final String name,
                    final String description,
                    final boolean active,
                    final Instant createdAt,
                    final Instant updateAt,
                    final Instant deleteAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }


    public static Category newCategory(final String expectedName, final String expectedDescription, final boolean expectedIsActive) {
        final var id = UUID.randomUUID().toString();
        final var now = Instant.now();
        return new Category(id, expectedName, expectedDescription, expectedIsActive, now, now, null);
    }

    public String getId() {
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