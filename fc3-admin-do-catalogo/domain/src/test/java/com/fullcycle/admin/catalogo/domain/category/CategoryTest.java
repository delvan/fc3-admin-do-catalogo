package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handle.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);

        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());

        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdateAt());
        Assertions.assertNull(actualCategory.getDeleteAt());
        //Assertions.assertNotNull(new Category());

    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShould() {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, ()
                -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }


    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShould() {

        final var expectedName = "  ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, ()
                -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());


    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShould() {

        final var expectedName = "Fi ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, ()
                -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());


    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShould() {

        final var expectedName = """
                A certificação de metodologias que nos auxiliam a lidar com a
                 valorização de fatores subjetivos oferece uma interessante 
                 oportunidade para verificação das diversas correntes de pensamento.
                  Ainda assim, existem dúvidas a respeito de como o 
                  fenômeno da Internet faz parte de um processo de 
                  gerenciamento do levantamento das variáveis envolvidas. 
                  Acima de tudo, é fundamental ressaltar que a mobilidade 
                  dos capitais internacionais causa impacto indireto na 
                  reavaliação dos índices pretendidos. Por outro lado, o 
                  desafiador cenário globalizado promove a alavancagem do 
                  sistema de participação geral. O que temos que ter sempre em 
                  mente é que a contínua expansão de nossa atividade pode nos levar a considerar 
                  a reestruturação das condições financeiras e administrativas exigidas.
                """;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, ()
                -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShould() {

        final var expectedName = "Filmes";
        final var expectedDescription = "     ";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

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

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShould() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(aCategory);
        Assertions.assertNotNull(aCategory.getId());
        Assertions.assertEquals(expectedName, aCategory.getName());
        Assertions.assertEquals(expectedDescription, aCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, aCategory.isActive());
        Assertions.assertNotNull(aCategory.getCreatedAt());
        Assertions.assertNotNull(aCategory.getUpdateAt());
        Assertions.assertNotNull(aCategory.getDeleteAt());
        //Assertions.assertNotNull(new Category());

    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updateAt = aCategory.getUpdateAt();
        final var createdAt = aCategory.getCreatedAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeleteAt());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updateAt));
        Assertions.assertNotNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> 
        aCategory.validate(new ThrowsValidationHandler()));

        final var updateAt = aCategory.getUpdateAt();
        final var createdAt = aCategory.getCreatedAt();

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeleteAt());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updateAt));
        Assertions.assertNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdate() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Film", "A categoaria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updateAt = aCategory.getUpdateAt();
        final var createdAt = aCategory.getCreatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updateAt));
        Assertions.assertNull(aCategory.getDeleteAt());

    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdate() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory("Film", "A categoaria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
        
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeleteAt());

        final var updateAt = aCategory.getUpdateAt();
        final var createdAt = aCategory.getCreatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updateAt));
        Assertions.assertNotNull(aCategory.getDeleteAt());

    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdate() {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Filmes", "A categoaria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updateAt = aCategory.getUpdateAt();
        final var createdAt = aCategory.getCreatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        //Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updateAt));
        Assertions.assertNull(aCategory.getDeleteAt());

    }
}
