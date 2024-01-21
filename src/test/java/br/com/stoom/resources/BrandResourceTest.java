package br.com.stoom.resources;

import br.com.stoom.ResourceTest;
import br.com.stoom.dto.request.ChangeStatusRequest;
import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateBrandRequest;
import br.com.stoom.dto.request.CreateBrandRequestSet;
import br.com.stoom.entities.BrandEntity;
import br.com.stoom.entities.CategoryEntity;
import br.com.stoom.services.brand.BrandService;
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

@WebMvcTest(BrandResource.class)
public class BrandResourceTest extends ResourceTest {

    private static final String URL = "/api/brands";

    @MockBean
    private BrandService brandService;

    @Test
    @DisplayName(
            """

                Dado que eu tenho os dados necessarios para criar uma marca
                Quando o serviço de criacao de marca for invocado com esses dados
                Entao uma nova marca deve ser criada com sucesso

            """
    )
    public void createBrand() throws Exception {

        final var brand = new CreateBrandRequest(
                "Marca00155559",
                true
        );

        final var input = new CreateBrandRequestSet(Set.of(brand));

        final var request = post(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(brandService).createBrand(
                argThat(
                        b -> b.stream().findFirst().get().getName().equals(brand.name()) &&
                                b.stream().findFirst().get().isActive() == brand.active())
                );


    }

    @Test
    @DisplayName(
            """

                Dado uma marca com status ativo
                Quando o endpoint de mudanca de status for chamado
                Entao essa marca tem seu status alterado

            """
    )
    public void changeBrandStatus() throws Exception {

        final var brandForUpdate = new ChangeStatusRequest(1L, false);
        final var input = new ChangeStatusRequestSet(Set.of(brandForUpdate));

        final var request = patch(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(brandService).changeBrandStatus(argThat(map ->
                map.containsKey(1L) && map.containsValue(Boolean.FALSE)
        ));

    }

    @Test
    @DisplayName(
            """

                Dado uma marca criada anteriormente
                Quando o endpoint de busca por id for chamado
                Entao essa marca e recuperada com sucesso

            """
    )
    public void findBrandById() throws Exception {

        when(brandService.findBrandById(4L))
                .thenReturn(new BrandEntity(4L, "Marca04"));

        final var request = get(URL.concat("/v1/{brandId}"), 4L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("id").value(4L))
                .andExpect(jsonPath("name").value("Marca04"));

    }

    @Test
    @DisplayName(
            """

                Dado uma marca anteriormente cadastrada
                Quando o endpoint de atualizacao de marca for chamado
                Entao essa marca deve ser atualizada

            """
    )
    public void updateBrand() throws Exception {

        final var input = new CategoryEntity(1L,"Marca01 atualizada");

        final var request = put(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(brandService).updateBrand(argThat(
                b -> b.getId().equals(input.getId()) &&
                        b.getName().equals(input.getName())
        ));

    }

}
