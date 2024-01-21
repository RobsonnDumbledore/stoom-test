package br.com.stoom.resources;

import br.com.stoom.ResourceTest;
import br.com.stoom.dto.request.ChangeStatusRequest;
import br.com.stoom.dto.request.ChangeStatusRequestSet;
import br.com.stoom.dto.request.CreateProductRequest;
import br.com.stoom.dto.request.CreateProductRequestSet;
import br.com.stoom.dto.request.UpdateProductRequest;
import br.com.stoom.entities.BrandEntity;
import br.com.stoom.entities.CategoryEntity;
import br.com.stoom.entities.ProductEntity;
import br.com.stoom.services.product.ProductService;
import br.com.stoom.utils.JsonUtils;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

@WebMvcTest(ProductResource.class)
public class ProductResourceTest extends ResourceTest {

    private static final String URL = "/api/products";

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName(
            """
                                            
                Dado que eu tenho os dados necessarios para criar um produto
                Quando o endpoint de criacao de produto e invocado com esses dados
                Entao um novo produto deve ser criado com sucesso
                                                
            """
    )
    public void createProduct() throws Exception {

        final var product01 = new CreateProductRequest(
                "Produto666",
                "Descrição do produto 666",
                100.50,
                true,
                5L,
                Set.of(5L)
        );

        final var input = new CreateProductRequestSet(Set.of(product01));

        final var request = post(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(productService).createProduct(argThat(set ->
                set.stream().anyMatch(b ->
                        b.getName().equals(product01.name()) &&
                                b.getDescription().equals(product01.description()) &&
                                b.getPrice() == (product01.price()) &&
                                b.isActive() == product01.active())
                )
        );

    }

    @Test
    @DisplayName(
            """

                Dado que um produto criado anteriormente
                Quando o endpoint de busca por id for chamado
                Entao esse produto e recuperado com sucesso

            """
    )
    public void findProductById() throws Exception {

        when(productService.findProductById(4L))
                .thenReturn(new ProductEntity(
                        4L,
                        "Produto0099",
                        "Descricao do produto 0099",
                        100.50,
                        true,
                        new BrandEntity(4L, "Marca0018"),
                        Set.of(new CategoryEntity(4L, "Categoria0018"))
                ));

        final var request = get(URL.concat("/v1/{productId}"), 4L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

       final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

       response
               .andExpect(jsonPath("id").value(4L))
               .andExpect(jsonPath("name").value("Produto0099"))
               .andExpect(jsonPath("description").value("Descricao do produto 0099"))
               .andExpect(jsonPath("price").value(100.5))
               .andExpect(jsonPath("active").value(true))
               .andExpect(jsonPath("brand.id").value(4L))
               .andExpect(jsonPath("brand.name").value("Marca0018"))
               .andExpect(jsonPath("categories[0].id").value(4L))
               .andExpect(jsonPath("categories[0].name").value("Categoria0018"));

    }

    @Test
    @DisplayName(
            """

                Dado que um produto com status ativo
                Quando o endpoint de mudanca de status for chamado
                Entao esse produto tem seu status alterado

            """
    )
    public void changeProductStatus() throws Exception {

        final var input = new ChangeStatusRequestSet(
                Set.of(new ChangeStatusRequest(4L, false))
        );

        final var request = patch(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(productService).changeProductStatus(argThat(map ->
                map.containsKey(4L) && map.containsValue(Boolean.FALSE)
        ));

    }

    @Test
    @DisplayName(
            """

                Dado um produto anteriormente cadastrado
                Quando o endpoint de atualizacao de produto for chamado
                Entao esse produto deve ser atualizado

            """
    )
    public void updateProduct() throws Exception {

        final var input = new UpdateProductRequest(
                4L,
                "Produto 05 atualizado",
                "Descrição do produto 05",
                200.50,
                true,
                4L,
                Set.of(4L)
        );

        final var request = put(URL.concat("/v1"))
                .content(JsonUtils.json(input))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(productService).updateProduct(argThat(product ->
                product.getId().equals(4L) &&
                        product.getName().equals("Produto 05 atualizado") &&
                        product.getDescription().equals("Descrição do produto 05") &&
                        product.getPrice() == (200.50) &&
                        product.isActive()&&
                        product.getBrand().getId().equals(4L)));

    }

    @Test
    @DisplayName(
            """

                Dado que uma lista de produtos ja cadastrados
                Quando o endpoint de busca por categoria for chamado
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao esses produtos devem ser listados

            """
    )
    public void findProductsByCategory() throws Exception {

        List<ProductEntity> testProducts = List.of(
                new ProductEntity(1L, "Produto 1", "Descricao do produto 1", 10.00, true, new BrandEntity(), new HashSet<>()),
                new ProductEntity(2L, "Produto 2", "Descricao do produto 2", 20.00, true, new BrandEntity(), new HashSet<>()),
                new ProductEntity(3L, "Produto 3", "Descricao do produto 3", 30.00, true, new BrandEntity(), new HashSet<>())
        );

        when(productService.findProductByCategory(anyString(), any(SearchQuery.class)))
                .thenReturn(new Pagination<>(testProducts, 0, 10, 3, 1));

        final var request = get(URL.concat("/v1/category"))
                .queryParam("category", "ELETRONICOS")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("content[0].id").value(1L))
                .andExpect(jsonPath("content[0].name").value("Produto 1"))
                .andExpect(jsonPath("content[0].description").value("Descricao do produto 1"))
                .andExpect(jsonPath("content[0].price").value(10.0))
                .andExpect(jsonPath("content[0].active").value(true))
                .andExpect(jsonPath("currentPage").value(0))
                .andExpect(jsonPath("totalElements").value(3))
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("size").value(10));
    }

    @Test
    @DisplayName(
            """

                Dado que uma lista de produtos ja cadastrados
                Quando o endpoint de busca por marca for chamado
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao esses produtos devem ser listados

            """
    )
    public void findProductsByBrand() throws Exception {

        List<ProductEntity> testProducts = List.of(
                new ProductEntity(1L, "Produto 1", "Descricao do produto 1", 10.00, true, new BrandEntity(), new HashSet<>()),
                new ProductEntity(2L, "Produto 2", "Descricao do produto 2", 20.00, true, new BrandEntity(), new HashSet<>()),
                new ProductEntity(3L, "Produto 3", "Descricao do produto 3", 30.00, true, new BrandEntity(), new HashSet<>())
        );

        when(productService.findProductByBrand(anyString(), any(SearchQuery.class)))
                .thenReturn(new Pagination<>(testProducts, 0, 10, 3, 1));

        final var request = get(URL.concat("/v1/brand"))
                    .queryParam("brand", "MARCA01")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("content[0].id").value(1L))
                .andExpect(jsonPath("content[0].name").value("Produto 1"))
                .andExpect(jsonPath("content[0].description").value("Descricao do produto 1"))
                .andExpect(jsonPath("content[0].price").value(10.0))
                .andExpect(jsonPath("content[0].active").value(true))
                .andExpect(jsonPath("currentPage").value(0))
                .andExpect(jsonPath("totalElements").value(3))
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("size").value(10));
    }

    @Test
    @DisplayName(
            """

                Dado que uma lista de produtos ja cadastrados
                Quando o endpoint de busca for chamado
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao todos produtos devem ser listados

            """
    )
    public void findAllProducts() throws Exception {

        List<ProductEntity> testProducts = List.of(
                new ProductEntity(1L, "Produto 1", "Descricao do produto 1", 10.00, true, new BrandEntity(), new HashSet<>()),
                new ProductEntity(2L, "Produto 2", "Descricao do produto 2", 20.00, true, new BrandEntity(), new HashSet<>()),
                new ProductEntity(3L, "Produto 3", "Descricao do produto 3", 30.00, true, new BrandEntity(), new HashSet<>())
        );

        when(productService.findAllProducts(anyString(), any(SearchQuery.class)))
                .thenReturn(new Pagination<>(testProducts, 0, 10, 3, 1));

        final var request = get(URL.concat("/v1"))
                .queryParam("productName", "")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("totalElements").value(3))
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("size").value(10));

    }

    @Test
    @DisplayName(
            """

                Dado que uma lista de produtos ja cadastrados
                Quando o endpoint de busca for chamado com o nome de um produto
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao todos produtos com esse nome devem ser listados

            """
    )
    public void findAllProductsByNameProduct() throws Exception {

        List<ProductEntity> testProducts = List.of(
                new ProductEntity(5L, "Produto05", "Descricao do produto 05", 199.99, true, new BrandEntity(), new HashSet<>())
        );

        when(productService.findAllProducts(anyString(), any(SearchQuery.class)))
                .thenReturn(new Pagination<>(testProducts, 0, 10, 1, 1));

        final var request = get(URL.concat("/v1"))
                .queryParam("productName", "Produto05")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON);

        final var response =  this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        response
                .andExpect(jsonPath("content[0].id").value(5L))
                .andExpect(jsonPath("content[0].name").value("Produto05"))
                .andExpect(jsonPath("content[0].description").value("Descricao do produto 05"))
                .andExpect(jsonPath("content[0].price").value(199.99))
                .andExpect(jsonPath("content[0].active").value(true))
                .andExpect(jsonPath("currentPage").value(0))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("size").value(10));

    }

}
