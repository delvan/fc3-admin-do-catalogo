package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.infrastructure.MySQLGatewayTest;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;


    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjectedDependencies() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(categoryRepository);
    }

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdateAt(), actualCategory.getUpdateAt());
        Assertions.assertEquals(aCategory.getDeleteAt(), actualCategory.getDeleteAt());
        Assertions.assertNull(actualCategory.getDeleteAt());

        final var actualEntity = categoryRepository.getById(aCategory.getId().getValue());

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdateAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeleteAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());


    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnaCategoryUpdated() {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Film", null, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualInvalidEntity = categoryRepository.getById(aCategory.getId().getValue());

        Assertions.assertEquals("Film", actualInvalidEntity.getName());
        Assertions.assertNull(actualInvalidEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualInvalidEntity.isActive());

        Assertions.assertEquals(1, categoryRepository.count());

        final var aUpdateCategory = aCategory.clone().update(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = categoryGateway.update(aUpdateCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdateAt().isBefore(actualCategory.getUpdateAt()));
        Assertions.assertEquals(aCategory.getDeleteAt(), actualCategory.getDeleteAt());
        Assertions.assertNull(actualCategory.getDeleteAt());

        final var actualEntity = categoryRepository.getById(aCategory.getId().getValue());

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdateAt().isBefore(actualCategory.getUpdateAt()));
        Assertions.assertEquals(aCategory.getDeleteAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());


    }

    @Test
    public void givenAPrePersistedCategoryAndValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        final var aCategory = Category.newCategory("Filmes", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        categoryGateway.deleteById(aCategory.getId());

        Assertions.assertEquals(0, categoryRepository.count());

    }

    @Test
    public void givenAInvalidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {


        Assertions.assertEquals(0, categoryRepository.count());

        categoryGateway.deleteById(CategoryID.from("invalid"));

        Assertions.assertEquals(0, categoryRepository.count());


    }


    @Test
    public void givenAPrePersistedCategoryAndValidCategoryId_whenCallsFindById_shouldReturnACategory() {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualCategory = categoryGateway.findById(aCategory.getId()).get();

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdateAt(), actualCategory.getUpdateAt());
        Assertions.assertEquals(aCategory.getDeleteAt(), actualCategory.getDeleteAt());
        Assertions.assertNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenValidCategoryIdNoStored_whenCallsFindById_shouldReturnEmpty() {

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.findById(CategoryID.from("empty"));

        Assertions.assertTrue(actualCategory.isEmpty());

    }

    @Test
    public void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Series", null, true);
        final var documentarios = Category.newCategory("Documentarios", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(

                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)

        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");

        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());


    }

    @Test
    public void givenEmptyCategoriesTable_whenCallsFindAll_shouldReturnEmptyPage() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");

        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(0, actualResult.items().size());


    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnPaginated() {

        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Series", null, true);
        final var documentarios = Category.newCategory("Documentarios", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(

                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)

        ));

        //Page 0

        Assertions.assertEquals(3, categoryRepository.count());

        var query = new CategorySearchQuery(0, 1, "", "name", "asc");

        var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());


        //page 1

        expectedPage = 1;
        query = new CategorySearchQuery(1, 1, "", "name", "asc");

        actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());


        //page 2

        expectedPage = 2;
        query = new CategorySearchQuery(2, 1, "", "name", "asc");

        actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(series.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndDocsAsTerms_whenCallsFindAllAndTermsMathsCategoriesName_shouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Series", null, true);
        final var documentarios = Category.newCategory("Documentarios", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(

                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)

        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "doc", "name", "asc");

        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());


    }

    @Test
    public void givenPrePersistedCategoriesAndMaisAssistidaAsTerms_whenCallsFindAllAndTermsMathsCategoriesDescription_shouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var series = Category.newCategory("Series", "Uma categia assistida", true);
        final var documentarios = Category.newCategory("Documentarios", "A categoria menos assistida", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(

                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)

        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "MAIS ASSISTIDA", "name", "asc");

        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());


    }


}

