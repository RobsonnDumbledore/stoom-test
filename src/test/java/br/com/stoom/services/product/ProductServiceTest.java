package br.com.stoom.services.product;

import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import br.com.stoom.utils.SearchQuery;
import br.com.stoom.PostgresGatewayTest;
import br.com.stoom.entities.BrandEntity;
import org.junit.jupiter.api.DisplayName;
import br.com.stoom.entities.ProductEntity;
import br.com.stoom.entities.CategoryEntity;
import br.com.stoom.repositories.ProductRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceTest extends PostgresGatewayTest {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProductServiceTest(ProductService productService, ProductRepository productRepository, EntityManager entityManager) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }


    @Test
    @DisplayName(
            """
                                            
                Dado que eu tenho os dados necessarios para criar um produto
                Quando o serviço de criacao de produto e invocado com esses dados
                Entao um novo produto deve ser criado com sucesso
                                                
            """
    )
    public void createProduct() {

        productRepository.deleteAll();

        final var product = new ProductEntity(
                "Produto 4",
                "Descrição do produto 4",
                4.99,
                0.0,
                true,
                null,
                "123456",
                new BrandEntity(1L),
                Set.of(new CategoryEntity(1L))
        );

        productRepository.count();
        productService.createProduct(Set.of(product));

        final var newProduct = productService.findProductById(product.getId());

        assertEquals(product.getName(), newProduct.getName());
        assertEquals(product.getDescription(), newProduct.getDescription());
        assertEquals(product.getPrice(), newProduct.getPrice());
        assertEquals(product.isActive(), newProduct.isActive());
        assertEquals(product.getBrand().getId(), newProduct.getBrand().getId());
        assertEquals(product.getCategories().size(), newProduct.getCategories().size());
    }

    @Test
    @DisplayName(
            """
                                            
                Dado que um produto criado anteriormente
                Quando o serviço de busca por id for chamado
                Entao esse produto e recuperado com sucesso
                                                
            """
    )
    public void findProductById() {

        final var newProduct = productService.findProductById(1L);
        final var expectedName = "Produto 1";
        final var expectedDescription = "Descricao do produto 1";
        final var expectedPrice = 99.99;
        final var expectedActive = true;
        final var expectedBrandId = 1L;
        final var expectedCategorySize = 1;

        assertEquals(expectedName, newProduct.getName());
        assertEquals(expectedDescription, newProduct.getDescription());
        assertEquals(expectedPrice, newProduct.getPrice());
        assertEquals(expectedActive, newProduct.isActive());
        assertEquals(expectedBrandId, newProduct.getBrand().getId());
        assertEquals(expectedCategorySize, newProduct.getCategories().size());
    }

    @Test
    @DisplayName(
            """
                                            
                Dado que um produto com status ativo
                Quando o serviço de mudanca de status for chamado
                Entao esse produto tem seu status alterado
                                                
            """
    )
    public void changeProductStatus() {

        final var product = productService.findProductById(1L);

        assertTrue(product.isActive());

        productService.changeProductStatus(Map.of(1L, false));

        final var newProduct = productService.findProductById(1L);

        assertFalse(newProduct.isActive());
    }

    @Test
    @DisplayName(
            """
                                            
                Dado um produto anteriormente cadastrado
                Quando o servico de atualizacao de produto for chamado
                Entao esse produto deve ser atualizado
                                                
            """
    )
    public void updateProduct() {

        ProductEntity productBeforeUpdate = productService.findProductById(1L);

        assertEquals("Produto 1", productBeforeUpdate.getName());
        assertEquals("Descricao do produto 1", productBeforeUpdate.getDescription());
        assertEquals(Double.valueOf(99.99), productBeforeUpdate.getPrice());
        assertTrue(productBeforeUpdate.isActive());

        ProductEntity updatedProduct = new ProductEntity(
                "Produto 1 Atualizado",
                "Descricao atualizada do produto 1",
                199.99,
                5.5,
                false,
                null,
                "123456",
                productBeforeUpdate.getBrand(),
                productBeforeUpdate.getCategories()
        );

        productBeforeUpdate.updateEntity(updatedProduct);

        productService.updateProduct(productBeforeUpdate);

        ProductEntity productAfterUpdate = productService.findProductById(1L);

        assertEquals("Produto 1 Atualizado", productAfterUpdate.getName());
        assertEquals("Descricao atualizada do produto 1", productAfterUpdate.getDescription());
        assertEquals(Double.valueOf(199.99), productAfterUpdate.getPrice());
        assertFalse(productAfterUpdate.isActive());

    }

    @Test
    @DisplayName(
            """
                                            
                Dado alguns produtos anteriormente cadastrados
                Quando o servico de remocao de produto for chamado
                Entao esses produtos deve ser removidos
                                                
            """
    )
    public void removeProducts() {

        var products = Set.of(
                new ProductEntity(
                        "Produto 1",
                        "Descricao do produto 1",
                        99.99,
                        0.0,
                        true,
                        null,
                        "123456",
                        null,
                        null
                ),
                new ProductEntity(
                        "Produto 2",
                        "Descricao do produto 2",
                        99.99,
                        0.0,
                        true,
                        null,
                        "123456",
                        null,
                        null
                )
        );

        var savedProducts = productRepository.saveAll(products);

        var product01 = savedProducts.get(0);
        var product02 = savedProducts.get(1);

        productService.deleteProduct(Set.of(product01.getId(), product02.getId()));

        entityManager.flush();
        entityManager.clear();

        assertTrue(productRepository.findById(product01.getId()).isEmpty());
        assertTrue(productRepository.findById(product02.getId()).isEmpty());

    }

    @Test
    @DisplayName(
            """
                                            
                Dado que uma lista de produtos ja cadastrados
                Quando o servico de busca por categoria for chamado
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao esses produtos devem ser listados
                                                
            """
    )
    public void findProductsByCategory() {

        final var page = SearchQuery.with(0, 10, "", "price", "ASC");
        final var product = productService
                .findProductByCategory("ELETRONICOS", page)
                .content()
                .stream().findFirst();

        final var category = product.get()
                .getCategories()
                .stream()
                .findFirst();

        assertTrue(product.get().isActive());
        assertTrue(product.get().getBrand().isActive());
        assertTrue(category.get().isActive());
    }

    @Test
    @DisplayName(
            """
                                            
                Dado que uma lista de produtos ja cadastrados
                Quando o servico de busca por marca for chamado
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao esses produtos devem ser listados
                                                
            """
    )
    public void findProductsByBrand() {

        final var page = SearchQuery.with(0, 10, "", "price", "ASC");
        final var product = productService
                .findProductByBrand("Marca02", page)
                .content()
                .stream().findFirst();

        final var category = product.get()
                .getCategories()
                .stream()
                .findFirst();

        assertTrue(product.get().isActive());
        assertTrue(product.get().getBrand().isActive());
        assertTrue(category.get().isActive());
    }

    @Test
    @DisplayName(
            """
                                            
                Dado que uma lista de produtos ja cadastrados
                Quando o servico de busca for chamado
                E tanto categoria, marca e o proprio produto estiverem com status ativo
                Entao todos produtos devem ser listados
                                                
            """
    )
    public void findAllProducts() {

        final var expectedTotalElements = 3;

        final var page = SearchQuery.with(0, 10, "", "price", "ASC");
        final var product = productService
                .findAllProducts("", page);

        assertEquals(expectedTotalElements, product.content().size());

    }

}
