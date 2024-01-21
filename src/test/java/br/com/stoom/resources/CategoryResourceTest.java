package br.com.stoom.resources;

import br.com.stoom.ResourceTest;
import br.com.stoom.dto.request.ChangeStatusRequest;
import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateCategoryRequest;
import br.com.stoom.dto.request.CreateCategoryRequestSet;
import br.com.stoom.entities.CategoryEntity;
import br.com.stoom.services.category.CategoryService;
import br.com.stoom.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryResource.class)
public class CategoryResourceTest extends ResourceTest {

    private static final String URL = "/api/categories";

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName(
            """
                                    
                Dado que eu tenho os dados necessarios para criar uma categoria
                Quando o endpoint de criacao de categoria e invocado com esses dados
                Entao uma nova categoria deve ser criada com sucesso
                                        
            """
    )
    public void createCategory() throws Exception {

        final var category = new CreateCategoryRequest(
                "Categoria0015559",
                true
        );

        final var input = new CreateCategoryRequestSet(Set.of(category));

        final var request = post(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(categoryService).createCategory(
                argThat(
                        c -> c.stream().findFirst().get().getName().equals(category.name()) &&
                                c.stream().findFirst().get().isActive() == category.active())
        );

    }

    @Test
    @DisplayName(
            """

                Dado uma categoria com status ativo
                Quando o servico de mudanca de status for chamado
                Entao essa categoria tem seu status alterado

            """
    )
    public void changeCategoryStatus()  throws Exception {

        final var categoryForUpdate = new ChangeStatusRequest(1L, false);
        final var input = new ChangeStatusRequestSet(Set.of(categoryForUpdate));

        final var request = patch(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(categoryService).changeCategoryStatus(argThat(map ->
                map.containsKey(1L) && map.containsValue(Boolean.FALSE)
        ));

    }

    @Test
    @DisplayName(
            """

                Dado uma categoria criada anteriormente
                Quando o serviço de busca por id for chamado
                Entao essa categoria e recuperada com sucesso

            """
    )
    public void findCategoryById() throws Exception  {

        when(categoryService.findCategoryById(4L))
                .thenReturn(new CategoryEntity(4L, "ELETRONICOS"));

        final var request = get(URL.concat("/v1/{categoryId}"), 4L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("id").value(4L))
                .andExpect(jsonPath("name").value("ELETRONICOS"));

    }

    @Test
    @DisplayName(
            """

                Dado uma categoria anteriormente cadastrada
                Quando o endpoint de atualizacao de categoria for chamado
                Entao essa categoria deve ser atualizada

            """
    )
    public void updateCategory() throws Exception {

        final var input = new CategoryEntity(1L,"ELETRONICOS ATUALIZADOS");

        final var request = put(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(categoryService).updateCategory(argThat(
                b -> b.getId().equals(input.getId()) &&
                        b.getName().equals(input.getName())
        ));

    }
}
