package com.fullcycle.admin.catalogo.infrastructure.api;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalogo.ControllerTest;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;

import io.vavr.API;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

        @Autowired
        private MockMvc mvc;

        @MockBean
        private CreateCategoryUseCase createCategoryUseCase;

        @Autowired
        private ObjectMapper mapper;

        @Test
        public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {

                final var expectedName = "Filmes";
                final var expectedDescription = "A categoria mais assistida";
                final var expectedIsActive = true;

                final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

                when(createCategoryUseCase.execute(any()))
                                .thenReturn(API.Right(CreateCategoryOutput.from("123")));

                final var request = post("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.mapper.writeValueAsString(aInput));

                this.mvc.perform(request)
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", "/categories/123"))
                                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.id", equalTo("123"))

                                );

                verify(createCategoryUseCase, times(1)).execute(argThat(
                                cmd -> Objects.equals(expectedName, cmd.name()) &&
                                                Objects.equals(expectedDescription, cmd.description()) &&
                                                Objects.equals(expectedIsActive, cmd.isActive())

                ));

        }

}
