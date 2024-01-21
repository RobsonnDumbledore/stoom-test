package br.com.stoom.services.category;

import java.util.Map;
import java.util.Set;

import br.com.stoom.repositories.BrandRepository;
import br.com.stoom.repositories.CategoryRepository;
import br.com.stoom.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import br.com.stoom.PostgresGatewayTest;
import org.junit.jupiter.api.DisplayName;
import br.com.stoom.entities.CategoryEntity;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceTest extends PostgresGatewayTest {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryServiceTest(
            CategoryService categoryService,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ProductRepository productRepository
    ) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    @Test
    @DisplayName(
            """
                                    
                Dado que eu tenho os dados necessarios para criar uma categoria
                Quando o serviço de criacao de categoria e invocado com esses dados
                Entao uma nova categoria deve ser criada com sucesso
                                        
            """
    )
    public void createCategory() {

        productRepository.deleteAll();
        categoryRepository.deleteAll();
        brandRepository.deleteAll();

        final var category = new CategoryEntity(
                "Categoria 4450",
                true
        );

        categoryRepository.count();
        categoryService.createCategory(Set.of(category));

        final var newCategory = categoryService.findCategoryById(category.getId());

        assertEquals(category.getId(), newCategory.getId());
        assertEquals(category.getName(), newCategory.getName());
        assertEquals(category.isActive(), newCategory.isActive());

    }

    @Test
    @DisplayName(
            """
                                    
                Dado uma categoria com status ativo
                Quando o servico de mudanca de status for chamado
                Entao essa categoria tem seu status alterado
                                        
            """
    )
    public void changeCategoryStatus() {

        final var category = categoryService.findCategoryById(1L);

        assertTrue(category.isActive());

        categoryService.changeCategoryStatus(Map.of(1L, false));

        final var newCategory = categoryService.findCategoryById(category.getId());

        assertFalse(newCategory.isActive());
    }

    @Test
    @DisplayName(
            """
                                    
                Dado uma categoria criada anteriormente
                Quando o serviço de busca por id for chamado
                Entao essa categoria e recuperada com sucesso
                                        
            """
    )
    public void findCategoryById() {

        final var category = categoryService.findCategoryById(1L);

        assertEquals(1L, category.getId());
        assertEquals("ELETRONICOS", category.getName());
        assertTrue(category.isActive());
    }

    @Test
    @DisplayName(
            """
                                    
                Dado uma categoria anteriormente cadastrada
                Quando o servico de atualizacao de categoria for chamado
                Entao essa categoria deve ser atualizada
                                        
            """
    )
    public void updateCategory() {

        final var category = categoryService.findCategoryById(1L);

        assertEquals(1L, category.getId());
        assertEquals("ELETRONICOS", category.getName());
        assertTrue(category.isActive());

        final var updatedCategory = new CategoryEntity("ELETRONICOS ATUALIZADOS", true);

        category.updateCategory(updatedCategory);

        categoryService.updateCategory(category);

        final var categoryAfterUpdate = categoryService.findCategoryById(1L);

        assertEquals(1L, categoryAfterUpdate.getId());
        assertEquals("ELETRONICOS ATUALIZADOS", categoryAfterUpdate.getName());

    }

}
