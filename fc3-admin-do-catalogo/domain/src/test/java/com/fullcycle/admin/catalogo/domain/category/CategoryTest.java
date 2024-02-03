package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(aCategory);
        Assertions.assertNotNull(aCategory.getId());
        Assertions.assertEquals(expectedName, aCategory.getName());
        Assertions.assertEquals(expectedDescription, aCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, aCategory.isActive());
        Assertions.assertNotNull(aCategory.getCreatedAt());
        Assertions.assertNotNull(aCategory.getUpdateAt());
        Assertions.assertNull(aCategory.getDeleteAt());
        //Assertions.assertNotNull(new Category());

    }
}
