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
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
                                            
                Dado algumas marcas anteriormente cadastradas
                Quando o servico de remocao de marcas for chamado
                Entao essas marcas devem ser removidas
                                                
            """
    )
    public void removeBrandById() throws Exception {

        final var input = Set.of(1L,2L,3L);

        final var request = delete(URL.concat("/v1"))
                .queryParam("brandIds", "1,2,3")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        verify(brandService).deleteBrand(argThat( ids ->
                ids != null && input.containsAll(ids) && ids.containsAll(input)
        ));
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

    @Test
    @DisplayName(
            """
                                            
                Dado que uma lista de marcas ja cadastrados
                Quando o endpoint de busca for chamado
                E essas marcas estiverem com status ativo
                Entao todas marcas devem ser listadas
                                                
            """
    )
    public void findAllBrands() throws Exception {

        final var brands = List.of(
                new BrandEntity(1L, "ELETRONICOS", true),
                new BrandEntity(2L, "ROUPAS", true),
                new BrandEntity(3L,"BRINQUEDOS", true)
        );

        when(brandService.findAllBrands(anyString(), any(SearchQuery.class)))
                .thenReturn(new Pagination<>(brands, 0, 10, 3, 1));

        final var request = get(URL.concat("/v1"))
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("content[0].id").value(1L))
                .andExpect(jsonPath("content[0].name").value("ELETRONICOS"))
                .andExpect(jsonPath("content[0].active").value(true))
                .andExpect(jsonPath("content[1].id").value(2L))
                .andExpect(jsonPath("content[1].name").value("ROUPAS"))
                .andExpect(jsonPath("content[1].active").value(true))
                .andExpect(jsonPath("content[2].id").value(3L))
                .andExpect(jsonPath("content[2].name").value("BRINQUEDOS"))
                .andExpect(jsonPath("content[2].active").value(true))
                .andExpect(jsonPath("currentPage").value(0))
                .andExpect(jsonPath("totalElements").value(3))
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("size").value(10));

    }

}
