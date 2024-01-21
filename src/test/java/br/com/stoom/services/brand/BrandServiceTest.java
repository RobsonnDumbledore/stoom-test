package br.com.stoom.services.brand;

import java.util.Map;
import java.util.Set;

import br.com.stoom.repositories.BrandRepository;
import br.com.stoom.repositories.CategoryRepository;
import br.com.stoom.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import br.com.stoom.PostgresGatewayTest;
import br.com.stoom.entities.BrandEntity;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandServiceTest extends PostgresGatewayTest {

    private final BrandService brandService;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BrandServiceTest(
            BrandService brandService,
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Test
    @DisplayName(
            """
                                    
                Dado que eu tenho os dados necessarios para criar uma marca
                Quando o serviço de criacao de marca for invocado com esses dados
                Entao uma nova marca deve ser criada com sucesso
                                        
            """
    )
    public void createBrand() {

        productRepository.deleteAll();
        brandRepository.deleteAll();
        categoryRepository.deleteAll();

        final var brand = new BrandEntity(
                "Marca00960",
                true
        );

        System.out.println(brandRepository.count());

        brandService.createBrand(Set.of(brand));

        final var newBrand = brandService.findBrandById(brand.getId());

        assertEquals(brand.getId(), newBrand.getId());
        assertEquals(brand.getName(), newBrand.getName());
        assertEquals(brand.isActive(), newBrand.isActive());

    }

    @Test
    @DisplayName(
            """
                                    
                Dado uma marca com status ativo
                Quando o servico de mudanca de status for chamado
                Entao essa marca tem seu status alterado
                                        
            """
    )
    public void changeBrandStatus() {

        final var brand = brandService.findBrandById(1L);

        assertTrue(brand.isActive());

        brandService.changeBrandStatus(Map.of(1L, false));

        final var newBrand = brandService.findBrandById(brand.getId());

        assertFalse(newBrand.isActive());
    }

    @Test
    @DisplayName(
            """
                                    
                Dado uma marca criada anteriormente
                Quando o serviço de busca por id for chamado
                Entao essa marca e recuperada com sucesso
                                        
            """
    )
    public void findBrandById() {

        final var brand = brandService.findBrandById(1L);

        assertEquals(1L, brand.getId());
        assertEquals("Marca01", brand.getName());
        assertTrue(brand.isActive());
    }

    @Test
    @DisplayName(
            """
                                    
                Dado uma marca anteriormente cadastrada
                Quando o servico de atualizacao de marca for chamado
                Entao essa marca deve ser atualizada
                                        
            """
    )
    public void updateBrand() {

        final var brand = brandService.findBrandById(1L);

        assertEquals(1L, brand.getId());
        assertEquals("Marca01", brand.getName());
        assertTrue(brand.isActive());

        brand.setName("Marca001");

        brandService.updateBrand(brand);

        final var categoryAfterUpdate = brandService.findBrandById(1L);

        assertEquals(1L, categoryAfterUpdate.getId());
        assertEquals("Marca001", categoryAfterUpdate.getName());

    }

}
