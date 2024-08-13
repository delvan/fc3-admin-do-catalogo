package com.fullcycle.admin.catalogo.infrastructure.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalogo.ControllerTest;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.handle.Notification;
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

        @Test
        public void giveAInvalidName_whenCallsCreateCategory_thenShoulReturnNotification() throws Exception {

                final String expectedName = null;
                final var expectedDescription = "A categoria mais assistida";
                final var expectedIsActive = true;
                final var expectedMessage = "'name' should not be null";

                final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

                when(createCategoryUseCase.execute(any()))
                                .thenReturn(API.Left(Notification.create(new Error(expectedMessage))));

                final var request = post("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.mapper.writeValueAsString(aInput));
                                

                this.mvc.perform(request)
                                .andDo(print())
                                .andExpect(status().isUnprocessableEntity())
                                .andExpect(header().string("Location", Matchers.nullValue()))
                                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.errors", hasSize(1)))
                                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage))

                                );

                verify(createCategoryUseCase, times(1)).execute(argThat(
                                cmd -> Objects.equals(expectedName, cmd.name()) &&
                                                Objects.equals(expectedDescription, cmd.description()) &&
                                                Objects.equals(expectedIsActive, cmd.isActive())

                ));

        }

        @Test
        public void giveAInvalidCommand_whenCallsCreateCategory_thenShoulReturnDomainException() throws Exception {

                final String expectedName = null;
                final var expectedDescription = "A categoria mais assistida";
                final var expectedIsActive = true;
                final var expectedMessage = "'name' should not be null";

                final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

                when(createCategoryUseCase.execute(any()))
                                .thenThrow(DomainException.with(new Error(expectedMessage)));

                final var request = post("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.mapper.writeValueAsString(aInput));
                                

                this.mvc.perform(request)
                                .andDo(print())
                                .andExpect(status().isUnprocessableEntity())
                                .andExpect(header().string("Location", Matchers.nullValue()))
                                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.message", equalTo(expectedMessage)))
                                .andExpect(jsonPath("$.errors", hasSize(1)))
                                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage))

                                );

                verify(createCategoryUseCase, times(1)).execute(argThat(
                                cmd -> Objects.equals(expectedName, cmd.name()) &&
                                                Objects.equals(expectedDescription, cmd.description()) &&
                                                Objects.equals(expectedIsActive, cmd.isActive())

                ));

        }

}
